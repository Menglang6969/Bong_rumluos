package com.menglang.bong_rumluos.Bong_rumluos.services.invoice;
import com.menglang.bong_rumluos.Bong_rumluos.dto.invoice.*;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentRequestDTO;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Invoice;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Loan;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanRepayment;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CustomerRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.InvoiceRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanDetailsRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanRepository;
import com.menglang.bong_rumluos.Bong_rumluos.services.invoice.invoiceHelper.InvoiceCalculate;
import com.menglang.bong_rumluos.Bong_rumluos.services.invoice.invoiceHelper.InvoiceValidateData;
import com.menglang.bong_rumluos.Bong_rumluos.services.loanDetails.LoanDetailsServiceImpl;
import com.menglang.bong_rumluos.Bong_rumluos.utils.PageableResponse;
import com.menglang.bong_rumluos.Bong_rumluos.utils.SequenceKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private static final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);
    private final InvoiceRepository invoiceRepository;
    private final LoanRepository loanRepository;
    private final InvoiceMapper invoiceMapper;
    private final CustomerRepository customerRepository;
    private final LoanDetailsRepository loanDetailsRepository;
    private final SequenceKeyGenerator sequenceKeyGenerator;
    private final LoanDetailsServiceImpl loanDetailsService;
    private final InvoiceCalculate invoiceCalculate;
    private final InvoiceValidateData invoiceValidateData;

    @Override
    @Transactional
    public InvoiceResponseDTO makeInvoice(InvoiceRequestDTO requestDTO) throws BadRequestException {
        Invoice invoice = invoiceMapper.toInvoice(requestDTO, customerRepository, loanDetailsRepository, invoiceRepository);

        Set<LoanRepayment> loanRepaymentSet = invoiceValidateData.extractLoanRepayment(requestDTO.loanRepayments(), invoice);
        invoice.setLoanRepayments(loanRepaymentSet);
        invoice.setInvoiceNo(sequenceKeyGenerator.generateInvoiceKey());
        InvoiceTotalAmountResponse totalAmountTemplate = invoiceCalculate.calculateTotalAmountLoan(loanRepaymentSet);

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

    @Override
    @Transactional
    public InvoiceResponseDTO makeInvoiceByLoan(InvoiceRequestByLoan dto) throws BadRequestException {
        Loan loan=this.loanRepository.findById(dto.loan_id()).orElseThrow(()->new NotFoundException("Loan not Found."));

        Invoice invoice=Invoice.builder()
                .customer(loan.getCustomer())
                .description(dto.description())
                .totalPenalty(BigDecimal.ZERO)
                .totalAmount(loan.getDeposit())
                .totalPayment(loan.getDeposit())
                .invoiceNo(sequenceKeyGenerator.generateInvoiceKey())
                .loanRepayments(new HashSet<>())
                .build();
        try{
            Invoice savedInvoice= invoiceRepository.save(invoice);
            loan.setLoanStatus(LoanStatus.FINISH);
            loanRepository.save(loan);
            return invoiceMapper.toResponse(savedInvoice);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public Page<Invoice> getAllInvoices(int page, int size, String orderBy, String sortBy, String query) {
        Pageable pageable= PageableResponse.handlePageable(page,size,orderBy,sortBy);
        return invoiceRepository.getAllInvoiceByInvoiceNoOrCustomerName(query,pageable);
    }

    private void updateLoanStatus(List<RepaymentRequestDTO> loanDetails) {
        for (RepaymentRequestDTO loanDetail : loanDetails) {
            loanDetailsService.updateLoanDetailsStatusAndPenalty(loanDetail.loanDetails(), loanDetail.penaltyRate());
        }
    }

}
