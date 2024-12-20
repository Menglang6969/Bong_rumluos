package com.menglang.bong_rumluos.Bong_rumluos.services.laon;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanDto;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanSchedulerResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Loan;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanDetails;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CustomerRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.ProductRepository;
import com.menglang.bong_rumluos.Bong_rumluos.services.laon.laonCalculate.LoanCalculateService;
import com.menglang.bong_rumluos.Bong_rumluos.services.laon.laonCalculate.LoanFactory;
import com.menglang.bong_rumluos.Bong_rumluos.utils.SequenceKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Lazy
@RequiredArgsConstructor
public class LoanDeposit {
    private final SequenceKeyGenerator sequenceKeyGenerator;
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final LoanFactory loanFactory;

    public LoanResponse payAll(Loan loan) {
        loan.setLoanStatus(LoanStatus.WAITING);
        loan.setLoanDetails(new HashSet<>());
        loan.setRate((short) 0);
        loan.setTotalAmount(loan.getDeposit());
        loan.setTotalInterest(BigDecimal.ZERO);
        loan.setLoanKey(sequenceKeyGenerator.generateLoanKey());

        try {
            Loan savedLoan = loanRepository.save(loan);
            return loanMapper.toLoanResponse(savedLoan);

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public LoanResponse pay(LoanDto loanDto) {

        Loan loan = loanMapper.toLoan(loanDto, customerRepository, productRepository);
        LoanCalculateService loanProcess = loanFactory.getPaymentService(loanDto.getType());
        List<LoanSchedulerResponse> loanSchedulerResponse = loanProcess.loanCalculator(loanDto.getPrincipal(), loanDto.getRate(), loanDto.getStartDate(), loanDto.getEndDate());

        Set<LoanDetails> setLoanDetails = new HashSet<>();
        BigDecimal totalInterestAndPrincipal = BigDecimal.ZERO;

        if (!loanSchedulerResponse.isEmpty()) {
            for (LoanSchedulerResponse emi : loanSchedulerResponse) {
                LoanDetails loanDetails = extractLoanDetails(emi, loan);
                totalInterestAndPrincipal = totalInterestAndPrincipal.add(emi.getPrincipalPayment()).setScale(2, RoundingMode.HALF_UP);
                setLoanDetails.add(loanDetails);
            }
        }
        loan.setLoanStatus(LoanStatus.PROCESSING);
        loan.setLoanDetails(new HashSet<>((setLoanDetails.stream().toList())));
        loan.setTotalAmount(totalInterestAndPrincipal);
        loan.setTotalInterest(totalInterestAndPrincipal.subtract(loan.getPrincipal()));
        loan.setLoanKey(sequenceKeyGenerator.generateLoanKey());

        try {
            Loan savedLoan = loanRepository.save(loan);
            return loanMapper.toLoanResponse(savedLoan);

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private LoanDetails extractLoanDetails(LoanSchedulerResponse emi, Loan loan) {
        LoanDetails loanDetails = new LoanDetails();
        loanDetails.setLoan(loan);
        loanDetails.setStatus(LoanStatus.WAITING);
        loanDetails.setPrincipal(emi.getPrincipalPayment());
        loanDetails.setInterestPayment(emi.getInterestPayment());
        loanDetails.setOutstandingBalance(emi.getOutstandingBalance());
        loanDetails.setRepaymentDate(emi.getRepaymentDate());
        loanDetails.setInterestCap(emi.getInterestCap());
        return loanDetails;
    }

}
