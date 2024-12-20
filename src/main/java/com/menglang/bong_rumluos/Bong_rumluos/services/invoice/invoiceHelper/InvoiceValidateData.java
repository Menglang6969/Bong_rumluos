package com.menglang.bong_rumluos.Bong_rumluos.services.invoice.invoiceHelper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.invoice.InvoiceMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentRequestDTO;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Invoice;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanRepayment;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.InvoiceRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Lazy
public class InvoiceValidateData {
    private final InvoiceMapper invoiceMapper;
    private final LoanDetailsRepository loanDetailsRepository;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceCalculate invoiceCalculate;

    public Set<LoanRepayment> extractLoanRepayment(List<RepaymentRequestDTO> repaymentRequestDTOS, Invoice invoice) {
        Set<LoanRepayment> loanRepaymentSet = new HashSet<>();
        for (RepaymentRequestDTO repaymentReq : repaymentRequestDTOS) {
            LoanRepayment repayment = invoiceMapper.toLoanRepayment(repaymentReq, loanDetailsRepository, invoiceRepository);
            if (repayment.getLoanDetails().getStatus().equals(LoanStatus.DONE)) continue;//skip for loan details already pay

            //validate repayment amount with principal
            validateRepaymentAmount(repayment.getAmountRepay(), repayment.getLoanDetails().getPrincipal());
            //set penalty if penaltyRate greater than 0
            repayment.setPenalty(invoiceCalculate.calculatePenalty(repaymentReq.amountRepay(),repayment.getPenaltyRate(),repayment.getLoanDetails().getRepaymentDate()));
            repayment.setInvoiceId(invoice);
            loanRepaymentSet.add(repayment);
        }
        return loanRepaymentSet;
    }


    public void validateRepaymentAmount(BigDecimal amountRepay, BigDecimal principal) {
        if (amountRepay.compareTo(principal) < 0)
            throw new BadRequestException("Repayment must be equal to Loan repayment");
    }
}
