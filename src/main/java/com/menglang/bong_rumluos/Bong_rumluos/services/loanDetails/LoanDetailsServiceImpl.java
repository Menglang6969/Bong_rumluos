package com.menglang.bong_rumluos.Bong_rumluos.services.loanDetails;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Loan;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanDetails;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanDetailsRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LoanDetailsServiceImpl implements LoanDetailsService {
    private static final Logger log = LoggerFactory.getLogger(LoanDetailsServiceImpl.class);
    private final LoanDetailsRepository loanDetailsRepository;
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;


    @Override
    @Transactional
    public LoanResponse updateLoanDetailsStatus(Long id) {
        LoanDetails loanDetails = loanDetailsRepository.findById(id).orElseThrow(() -> new NotFoundException("Loan Details Not Found for this Data"));
        if (loanDetails.getStatus().equals(LoanStatus.DONE)) return null;

        loanDetails.setStatus(LoanStatus.DONE);
        loanDetailsRepository.save(loanDetails);

        return updateLoanStatus(loanDetails.getLoan().getId());
    }

    @Override
    @Transactional
    public void updateLoanDetailsStatusAndPenalty(Long id, Long penalty) {
        LoanDetails loanDetails = loanDetailsRepository.findById(id).orElseThrow(() -> new NotFoundException("Loan Details Not Found for this Data"));
        if (loanDetails.getStatus().equals(LoanStatus.DONE)) return;

        if (penalty != null && penalty > 0) {
            loanDetails.setIsPenalty(true);
        }

        loanDetails.setStatus(LoanStatus.DONE);
        loanDetailsRepository.save(loanDetails);

        updateLoanStatus(loanDetails.getLoan().getId());

    }

    @Override
    @Transactional
    public BigDecimal getOutStandingBalanceCurrentLoan(Long id) {
        List<LoanDetails> loanDetailsList=loanDetailsRepository.findAllByLoanId(id);
        LoanDetails loanDetailsMax =loanDetailsList.stream().max(Comparator.comparing(LoanDetails::getOutstandingBalance)).orElseThrow(NoSuchElementException::new);
        if (loanDetailsMax!=null){
            closeLoanAndLoanDetail(id,loanDetailsList);
            return loanDetailsMax.getOutstandingBalance();
        }
        return null;
    }


    //when restructure loan current loan change status to finish and all details change status to closed
    private void closeLoanAndLoanDetail(Long id,List<LoanDetails> loanDetailsList){
        try{
           Loan loan= loanRepository.findById(id).orElseThrow(()->new NotFoundException("Loan Not Found"));
           loan.setLoanStatus(LoanStatus.FINISH);
           loanRepository.save(loan);

           List<LoanDetails> loanDetailsTobeUpdate=new ArrayList<>();
           for (LoanDetails detail:loanDetailsList){
               detail.setStatus(LoanStatus.CLOSED);
               loanDetailsTobeUpdate.add(detail);
           }

           loanDetailsRepository.saveAll(loanDetailsTobeUpdate);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    private LoanResponse updateLoanStatus(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new NotFoundException("Loan Not Found"));
        long count = loan.getLoanDetails() == null
                ? 0
                : loan.getLoanDetails().stream()
                .filter(ld -> ld.getStatus().equals(LoanStatus.WAITING))
                .count();
        if (count == 0) {
            loan.setLoanStatus(LoanStatus.FINISH);
            loanRepository.save(loan);
        }
        return loanMapper.toLoanResponse(loan);
    }
}
