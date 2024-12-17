package com.menglang.bong_rumluos.Bong_rumluos.services.invoice;

import com.menglang.bong_rumluos.Bong_rumluos.dto.invoice.InvoiceMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.invoice.InvoiceRequestDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.invoice.InvoiceResponseDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.invoice.InvoiceTotalAmountResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentRequestDTO;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Invoice;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanRepayment;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CustomerRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.InvoiceRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanDetailsRepository;
import com.menglang.bong_rumluos.Bong_rumluos.services.loanDetails.LoanDetailsServiceImpl;
import com.menglang.bong_rumluos.Bong_rumluos.utils.SequenceKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private static final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final CustomerRepository customerRepository;
    private final LoanDetailsRepository loanDetailsRepository;
    private final SequenceKeyGenerator sequenceKeyGenerator;
    private final LoanDetailsServiceImpl loanDetailsService;

    @Override
    @Transactional
    public InvoiceResponseDTO makeInvoice(InvoiceRequestDTO requestDTO) throws BadRequestException {
        Invoice invoice = invoiceMapper.toInvoice(requestDTO, customerRepository, loanDetailsRepository, invoiceRepository);

        Set<LoanRepayment> loanRepaymentSet = extractLoanRepayment(requestDTO.loanRepayments(), invoice);
        invoice.setLoanRepayments(loanRepaymentSet);
        invoice.setInvoiceNo(sequenceKeyGenerator.generateInvoiceKey());
        InvoiceTotalAmountResponse totalAmountTemplate = calculateTotalAmountLoan(loanRepaymentSet);

        log.info("payment: {}, penalty: {}, total:{}", totalAmountTemplate.getTotalPayment(), totalAmountTemplate.getTotalPenalty(), totalAmountTemplate.getTotalAmount());

        invoice.setTotalAmount(totalAmountTemplate.getTotalAmount());
        invoice.setTotalPayment(totalAmountTemplate.getTotalPayment());
        invoice.setTotalPenalty(totalAmountTemplate.getTotalPenalty());

        try {
            Invoice savedInvoice = invoiceRepository.save(invoice);
            //update status both loan details and loan
            updateLoanStatus(requestDTO.loanRepayments().stream().toList());
            return invoiceMapper.toResponse(savedInvoice);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    private Set<LoanRepayment> extractLoanRepayment(List<RepaymentRequestDTO> repaymentRequestDTOS, Invoice invoice) {

        Set<LoanRepayment> loanRepaymentSet = new HashSet<>();
        for (RepaymentRequestDTO repaymentReq : repaymentRequestDTOS) {
            LoanRepayment repayment = invoiceMapper.toLoanRepayment(repaymentReq, loanDetailsRepository, invoiceRepository);
            if (repayment.getLoanDetails().getStatus().equals(LoanStatus.DONE)) continue;//skip for loan details already pay

            //validate repayment amount with principal
            validateRepaymentAmount(repayment.getAmountRepay(), repayment.getLoanDetails().getPrincipal());
            //set penalty if penaltyRate greater than 0
            repayment.setPenalty(calculatePenalty(repaymentReq.amountRepay(),repayment.getPenaltyRate(),repayment.getLoanDetails().getRepaymentDate()));
            repayment.setInvoiceId(invoice);
            loanRepaymentSet.add(repayment);
        }
        return loanRepaymentSet;
    }

    private InvoiceTotalAmountResponse calculateTotalAmountLoan(Set<LoanRepayment> loanRepaymentSet) {


        InvoiceTotalAmountResponse invoiceTotalAmountResponse = new InvoiceTotalAmountResponse(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        for (LoanRepayment loanRepayment : loanRepaymentSet) {
            invoiceTotalAmountResponse.setTotalPayment(invoiceTotalAmountResponse.getTotalPayment().add(loanRepayment.getAmountRepay()));
            invoiceTotalAmountResponse.setTotalPenalty(invoiceTotalAmountResponse.getTotalPenalty().add(loanRepayment.getPenalty()));
        }
        invoiceTotalAmountResponse.setTotalAmount(invoiceTotalAmountResponse.getTotalPayment().add(invoiceTotalAmountResponse.getTotalPenalty()));
        return invoiceTotalAmountResponse;
    }

    private void validateRepaymentAmount(BigDecimal amountRepay, BigDecimal principal) {

        log.info("amount repay: {} , Principal: {}", amountRepay, principal);
        if (amountRepay.compareTo(principal) < 0)
            throw new BadRequestException("Repayment must be equal to Loan repayment");
    }

    private void updateLoanStatus(List<RepaymentRequestDTO> loanDetails) {
        for (RepaymentRequestDTO loanDetail : loanDetails) {
            loanDetailsService.updateLoanDetailsStatusAndPenalty(loanDetail.loanDetails(),loanDetail.penaltyRate());
        }
    }

    //Penalty=Principal x dailyPenaltyRate x OverdueDay
    private BigDecimal calculatePenalty(BigDecimal principal, Long rate, LocalDate payDay){
        if (rate!=null && rate>0){
            int overdueDay= (int) ChronoUnit.DAYS.between(payDay,LocalDate.now());
            log.info("overdue day: {}",overdueDay);
            if(overdueDay>3){
                BigDecimal penaltyRate= BigDecimal.valueOf(rate/100);
                return principal.multiply(penaltyRate).multiply(new BigDecimal(overdueDay-3));
            }
            return  BigDecimal.ZERO;
        }
        return BigDecimal.ZERO;

    }
}
