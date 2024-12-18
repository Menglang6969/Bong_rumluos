package com.menglang.bong_rumluos.Bong_rumluos.services.laon;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.*;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Customer;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Loan;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanDetails;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.GenerateKey;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CustomerRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.ProductRepository;
import com.menglang.bong_rumluos.Bong_rumluos.services.generateKey.GenerateNumber;
import com.menglang.bong_rumluos.Bong_rumluos.services.generateKey.factory.GenerateKeyFactory;
import com.menglang.bong_rumluos.Bong_rumluos.services.laon.laonCalculate.LoanCalculateService;
import com.menglang.bong_rumluos.Bong_rumluos.services.laon.laonCalculate.LoanFactory;
import com.menglang.bong_rumluos.Bong_rumluos.services.loanDetails.LoanDetailsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private static final Logger log = LoggerFactory.getLogger(LoanServiceImpl.class);
    private final LoanFactory loanFactory;
    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final LoanDetailsService loanDetailsService;
    private final ProductRepository productRepository;
    private final LoanMapper loanMapper;
    private final GenerateKeyFactory generateKeyFactory;

    @Override
    @Transactional
    public LoanResponse create(LoanDto loanDto) throws BadRequestException {

        log.info(" loan restructure: {}",loanDto.getType());
        LoanCalculateService loanProcess = loanFactory.getPaymentService(loanDto.getType());
        log.info(" loanProcess......");
        Loan loan = loanMapper.toLoan(loanDto, customerRepository,productRepository);
        log.info("loan: {}",loan.getCustomer().getId());
        List<LoanSchedulerResponse> loanSchedulerResponse = loanProcess.loanCalculator(loanDto.getPrincipal(), loanDto.getRate(), loanDto.getStartDate(), loanDto.getEndDate());

        Set<LoanDetails> setLoanDetails = new HashSet<>();
        BigDecimal totalInterestAndPrincipal = BigDecimal.ZERO;

        if (!loanSchedulerResponse.isEmpty()) {
            log.info("loan scheduler: {}",loanSchedulerResponse.get(0).getPrincipalPayment());

            for (LoanSchedulerResponse emi : loanSchedulerResponse) {
                LoanDetails loanDetails = extractLoanDetails(emi, loan);
                totalInterestAndPrincipal = totalInterestAndPrincipal.add(emi.getPrincipalPayment()).setScale(2, RoundingMode.HALF_UP);
                setLoanDetails.add(loanDetails);
            }
        }

        try {
            loan.setLoanStatus(LoanStatus.PROCESSING);
            loan.setLoanDetails(new HashSet<>((setLoanDetails.stream().toList())));
            loan.setTotalAmount(totalInterestAndPrincipal);
            loan.setTotalInterest(totalInterestAndPrincipal.subtract(loan.getPrincipal()));
            loan.setLoanKey(generateLoanKey());
            log.info("loan mapper: {}",loan.getLoanKey());
            Loan savedLoan = loanRepository.save(loan);
            return loanMapper.toLoanResponse(savedLoan);

        } catch (Exception e) {
            log.warn("loan error: {}",e.getLocalizedMessage());
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public LoanResponse view(long loanId) throws NotFoundException {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan Not Found"));
        return loanMapper.toLoanResponse(loan);
    }

    @Override
    public List<LoanResponse> findAll(int page, int limit, String orderBy, String sortBy, String query) throws BadRequestException {
        Sort.Direction order = orderBy.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(order, orderBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Loan> loansPage = loanRepository.findByLoanKeyOrCustomerNameOrPhone(query, pageable);
        return loansPage.getContent().stream().map(this.loanMapper::toLoanResponse).toList();
    }

    @Override
    public LoanResponse delete(long loanId) throws BadRequestException {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan Not Found"));
        if (loan != null) {
            loanRepository.delete(loan);
        }
        return this.loanMapper.toLoanResponse(loan);
    }

    @Override
    public List<LoanResponse> findByCustomerId(Long customer_id) throws BadRequestException {
        Customer customer = customerRepository.findById(customer_id).orElseThrow(() -> new NotFoundException("Customer Not Found"));
        List<Loan> loans = this.loanRepository.findByCustomerId(customer);
        return loans.stream().map(this.loanMapper::toLoanResponse).toList();
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

    private String generateLoanKey() {
        GenerateNumber generateKey = generateKeyFactory.getKeyGenerate(GenerateKey.LoanKey);
        return generateKey.generateKey();
    }


    @Override
    @Transactional
    public LoanResponse restructure(LoanRestructureDto restructureDto) throws BadRequestException {
        log.info(" loan id: {}",restructureDto.getLoanId());
        log.info(" loan type: {}",restructureDto.getType());
        BigDecimal outStandingBalance=loanDetailsService.getOutStandingBalanceCurrentLoan(restructureDto.getLoanId());
        if (outStandingBalance==null) throw new BadRequestException("This Loan Out of Payment pls check it again...");
        LoanDto loanDto=LoanDto.builder()
                .alert(restructureDto.getAlert())
                .customer_id(restructureDto.getCustomer_id())
                .rate(restructureDto.getRate())
                .term(restructureDto.getTerm())
                .product(restructureDto.getProduct())
                .type(restructureDto.getType())
                .penaltyRate(restructureDto.getPenaltyRate())
                .principal(outStandingBalance)
                .startDate(restructureDto.getStartDate())
                .endDate(restructureDto.getEndDate())
                .build();

            return this.create(loanDto);
    }


}
