package com.menglang.bong_rumluos.Bong_rumluos.services.loanDetails;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.loanDetails.LoanDetailsResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Loan;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanDetails;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanDetailsRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class LoanDetailsServiceImpl implements LoanDetailsService{
    private static final Logger log = LoggerFactory.getLogger(LoanDetailsServiceImpl.class);
    private final LoanDetailsRepository loanDetailsRepository;
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;

    @Override
    public LoanResponse updateLoanDetailsStatus(Long id) {
        LoanDetails loanDetails=loanDetailsRepository.findById(id).orElseThrow(()->new NotFoundException("Loan Details Not Found for this Data"));
        loanDetails.setStatus(LoanStatus.DONE);
        loanDetailsRepository.save(loanDetails);
        Loan loan=loanRepository.findById(loanDetails.getLoan().getId()).orElseThrow(()->new NotFoundException("Loan Not Found"));

        if (loanDetails.getOutstandingBalance().equals(BigDecimal.ZERO)){
             loan.setLoanStatus(LoanStatus.FINISH);
             loanRepository.save(loan);
        }

        return loanMapper.toLoanResponse(loan);
    }
}
