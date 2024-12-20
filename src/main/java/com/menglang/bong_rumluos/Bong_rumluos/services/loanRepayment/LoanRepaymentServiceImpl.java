package com.menglang.bong_rumluos.Bong_rumluos.services.loanRepayment;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentMapper;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.InvoiceRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanDetailsRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanRepaymentRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanRepository;
import com.menglang.bong_rumluos.Bong_rumluos.services.loanDetails.LoanDetailsService;
import com.menglang.bong_rumluos.Bong_rumluos.utils.SequenceKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

//    @Override
//    @Transactional
//    public InvoiceResponseDTO makeRepayment(InvoiceRequestByLoan dto) throws BadRequestException {
//
//        Loan loanRepayment = loanRepository.findById(dto.loan_id()).orElseThrow(()->new NotFoundException("Loan Not Found."));
////
//        return null;
//    }


}
