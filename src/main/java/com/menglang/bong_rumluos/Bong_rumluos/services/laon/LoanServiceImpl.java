package com.menglang.bong_rumluos.Bong_rumluos.services.laon;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanDto;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanRestructureDto;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Customer;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Loan;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Product;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CustomerRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.ProductRepository;
import com.menglang.bong_rumluos.Bong_rumluos.services.loanDetails.LoanDetailsService;
import com.menglang.bong_rumluos.Bong_rumluos.services.product.ProductService;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private static final Logger log = LoggerFactory.getLogger(LoanServiceImpl.class);

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final LoanDetailsService loanDetailsService;
    private final ProductRepository productRepository;
    private final LoanMapper loanMapper;
    private final ProductService productService;
    private final LoanDeposit loanDeposit;

    @Override
    @Transactional
    public LoanResponse create(LoanDto loanDto) throws BadRequestException {
        BigDecimal principal = productService.getProductPrice(loanDto.getProducts());//sum product price
        if (loanDto.getDeposit() != null && loanDto.getDeposit().compareTo(BigDecimal.ZERO) >= 0) {
            if (loanDto.getDeposit().compareTo(principal) > 0) loanDto.setPrincipal(BigDecimal.ZERO);
            else loanDto.setPrincipal(principal.subtract(loanDto.getDeposit()));

            if (loanDto.getDeposit().compareTo(principal) == 0) {
                Loan loan = loanMapper.toLoan(loanDto, customerRepository, productRepository);
                return loanDeposit.payAll(loan);
            } else {
                return loanDeposit.pay(loanDto);
            }
        } else throw new BadRequestException("Deposit is Null");

    }

    @Override
    public LoanResponse view(long loanId) throws NotFoundException {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan Not Found"));
        return loanMapper.toLoanResponse(loan);
    }

    @Override
    public Page<Loan> findAll(int page, int limit, String orderBy, String sortBy, String query) throws BadRequestException {
        Sort.Direction order = orderBy.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(order, orderBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        return loanRepository.findByLoanKeyOrCustomerNameOrPhone(query, pageable);

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

    @Override
    @Transactional
    public LoanResponse restructure(LoanRestructureDto restructureDto) throws BadRequestException {

        BigDecimal outStandingBalance = loanDetailsService.getOutStandingBalanceCurrentLoan(restructureDto.getLoanId());
        log.info(" outstanding balance: {}",outStandingBalance);
        if (outStandingBalance == null) throw new BadRequestException("This Loan Out of Payment pls check it again...");
        Loan existLoan=loanRepository.findById(restructureDto.getLoanId()).orElseThrow(()->new NotFoundException("Loan Not Found"));

        LoanDto loanDto = (LoanDto) LoanDto.builder()
                .principal(outStandingBalance)
                .customer_id(existLoan.getCustomer().getId())
                .rate(restructureDto.getRate())
                .term(restructureDto.getTerm())
                .products(existLoan.getProducts().stream().map(Product::getId).toList())
                .type(restructureDto.getType())
                .deposit(BigDecimal.ZERO)
                .startDate(restructureDto.getStartDate())
                .endDate(restructureDto.getEndDate())
                .build();

        return this.loanDeposit.pay(loanDto);
    }

}
