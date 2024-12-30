package com.menglang.bong_rumluos.Bong_rumluos.services.laon;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanDto;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanRestructureDto;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Loan;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoanService {
    LoanResponse create(LoanDto loanDto) throws BadRequestException;

    LoanResponse view(long loanId) throws NotFoundException;

    //    LoanResponse findByLoanKey(String loanKey) throws NotFoundException;
    Page<Loan> findAll(int page, int limit, String orderBy, String sortBy, String query) throws BadRequestException;

    LoanResponse delete(long loanId) throws BadRequestException;

    List<LoanResponse> findByCustomerId(Long customer_id) throws BadRequestException;

    LoanResponse restructure(LoanRestructureDto loanDto) throws BadRequestException;

}
