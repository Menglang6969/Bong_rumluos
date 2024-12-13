package com.menglang.bong_rumluos.Bong_rumluos.services.loanRepayment;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentCloseRequestDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentRequestDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentResponseDTO;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;

public interface LoanRepaymentService {
    RepaymentResponseDTO makeRepayment(RepaymentRequestDTO dto) throws BadRequestException;
    LoanResponse closeLoan(RepaymentCloseRequestDTO dto) throws BadRequestException;
}
