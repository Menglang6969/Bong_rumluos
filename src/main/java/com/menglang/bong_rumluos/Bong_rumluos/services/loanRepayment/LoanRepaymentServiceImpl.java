package com.menglang.bong_rumluos.Bong_rumluos.services.loanRepayment;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentCloseRequestDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentRequestDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentResponseDTO;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Loan;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanDetails;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanRepayment;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.InvoiceRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanDetailsRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanRepaymentRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanRepository;
import com.menglang.bong_rumluos.Bong_rumluos.services.loanDetails.LoanDetailsService;
import com.menglang.bong_rumluos.Bong_rumluos.utils.SequenceKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class LoanRepaymentServiceImpl implements LoanRepaymentService {
    private final LoanRepaymentRepository loanRepaymentRepository;
    private final RepaymentMapper repaymentMapper;
    private final LoanDetailsRepository loanDetailsRepository;
    private final LoanRepository loanRepository;
    private final LoanDetailsService loanDetailsService;
    private final InvoiceRepository invoiceRepository;
    private final SequenceKeyGenerator sequenceKeyGenerator;

    @Override
    @Transactional
    public RepaymentResponseDTO makeRepayment(RepaymentRequestDTO dto) throws BadRequestException {

//        LoanRepayment loanRepayment = repaymentMapper.toLoanRepayment(dto, loanDetailsRepository,invoiceRepository);
//        validateRepaymentAmount(dto.amountRepay(),loanRepayment.getLoanDetails().getPrincipal());
//        LoanRepayment savedRepayment = loanRepaymentRepository.save(loanRepayment);
//        loanDetailsService.updateLoanDetailsStatus(dto.loanDetails());
//        return repaymentMapper.toRepaymentResponse(savedRepayment);
        return null;
    }

    @Override
    @Transactional
    public LoanResponse closeLoan(RepaymentCloseRequestDTO dto) throws BadRequestException {
//        Loan loan = loanRepository.findById(dto.loan()).orElseThrow(() -> new NotFoundException("Loan Not Found."));
//        List<LoanDetails> loanDetailsList = loan.getLoanDetails().stream().filter(d -> LoanStatus.WAITING.equals(d.getStatus())).toList();
//        int months = loanDetailsList.size();
//        BigDecimal amountRepayment = dto.totalAmount().divide(BigDecimal.valueOf(months), 2, RoundingMode.HALF_UP);
//        BigDecimal penalty = BigDecimal.valueOf(dto.penalty() / months);
//
//        List<LoanRepayment> loanRepayments = new ArrayList<>();
//
//        for (LoanDetails loanDetail : loanDetailsList) {
//            RepaymentRequestDTO repaymentRequestDTO = RepaymentRequestDTO.builder()
//                    .repaymentDate(loanDetail.getRepaymentDate())
//                    .penalty(penalty)
//                    .amountRepay(amountRepayment)
//                    .loanDetails(loanDetail.getId())
//                    .build();
//            LoanRepayment loanRepayment = repaymentMapper.toLoanRepayment(repaymentRequestDTO, loanDetailsRepository,invoiceRepository);
////            loanRepayment.setTotalPayment(amountRepayment.add(penalty));
//
//            loanRepayments.add(loanRepayment);
//
//        }

        return null;
    }

    private void validateRepaymentAmount(BigDecimal amountRepay,BigDecimal principal ){
        if (amountRepay.compareTo(principal) < 0)
            throw new BadRequestException("Repayment must be equal to Loan repayment");
    }

}
