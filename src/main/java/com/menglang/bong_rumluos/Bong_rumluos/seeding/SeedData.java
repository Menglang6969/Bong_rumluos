package com.menglang.bong_rumluos.Bong_rumluos.seeding;

import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerRequest;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanDto;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanSchedulerResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.product.ProductMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.product.ProductRequest;
import com.menglang.bong_rumluos.Bong_rumluos.entities.*;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanType;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.Terms;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.*;
import com.menglang.bong_rumluos.Bong_rumluos.services.laon.laonCalculate.LoanCalculateService;
import com.menglang.bong_rumluos.Bong_rumluos.services.laon.laonCalculate.LoanFactory;
import com.menglang.bong_rumluos.Bong_rumluos.utils.SequenceKeyGenerator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class SeedData {

    private static final Logger log = LoggerFactory.getLogger(SeedData.class);
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final SequenceNumberRepository sequenceNumberRepository;
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final LoanFactory loanFactory;
    private final SequenceKeyGenerator sequenceKeyGenerator;

    @PostConstruct
    private void seedData() {
        seedCategory();
        seedProduct();
        seedCustomer();
        seedSequenceNumber();
        seedLoans();
    }

    private void seedLoans() {
        log.info("invoke seeding loans....");
        LoanDto loanDto1 = new LoanDto(new BigDecimal(10000), 10.0, Terms.SHORT, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 6, 1), (short) 3, 1L,List.of(1L), LoanType.EMILoan,  BigDecimal.ZERO,"");
        LoanDto loanDto2 = new LoanDto(new BigDecimal(12000), 5.0, Terms.SHORT, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 1), (short) 3, 1L,List.of(1L),  LoanType.EMILoan,  BigDecimal.ZERO,"");

        List<LoanDto> loanDtoList = List.of(loanDto1, loanDto2);
        try {
            LoanCalculateService loanProcess = loanFactory.getPaymentService(LoanType.EMILoan);

            List<Loan> loans = loanDtoList.stream().map(l -> loanMapper.toLoan(l, customerRepository,productRepository)).toList();
            for (Loan loan : loans) {
                List<LoanSchedulerResponse> loanSchedulerResponse = loanProcess.loanCalculator(loan.getPrincipal(), loan.getRate(), loan.getStartDate(), loan.getEndDate());

                Set<LoanDetails> setLoanDetails = new HashSet<>();
                BigDecimal totalInterestAndPrincipal = BigDecimal.ZERO;

                if (!loanSchedulerResponse.isEmpty()) {
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
                    loan.setLoanKey(sequenceKeyGenerator.generateLoanKey());

                    Loan savedLoan = loanRepository.save(loan);
                    loanMapper.toLoanResponse(savedLoan);

                } catch (Exception e) {
                    throw new BadRequestException(e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    private void seedSequenceNumber() {
        log.info("seeding sequence number . . . . . . . . . .");
        SequenceNumber sequenceNumber = new SequenceNumber(1L, BigDecimal.ZERO, BigDecimal.ZERO);
        try {
            SequenceNumber savedNumber = sequenceNumberRepository.save(sequenceNumber);
            log.info("sequence saved: {}", savedNumber.getLoanNumber());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private void seedCustomer() {
        log.info("seeding customer . . . . . . . . . .");
        CustomerRequest c1 = new CustomerRequest("Menglang", "012 223 322", "Kandal", "Web Developer", "https://localhost:9090/file/angkor_wat.jpg", List.of("https://localhost:9090/file/angkor_wat.jpg"));
        CustomerRequest c2 = new CustomerRequest("JongLong", "012 223 321", "Kandal", "Operation Officer", "https://localhost:9090/file/angkor_wat.jpg", List.of("https://localhost:9090/file/angkor_wat.jpg"));
        CustomerRequest c3 = new CustomerRequest("MengSorng", "012 223 329", "Kandal", "Sale Outdoor", "https://localhost:9090/file/angkor_wat.jpg", List.of("https://localhost:9090/file/angkor_wat.jpg"));
        List<Customer> customers = Stream.of(c1, c2, c3).map(this.customerMapper::toEntity).toList();
        try {
            customerRepository.saveAll(customers);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private void seedCategory() {
        log.info("seeding category . . . . . . . . . .");
        CategoryDTO cat1 = new CategoryDTO("Phone", "Smart Phone", "#eee", null);
        CategoryDTO cat2 = new CategoryDTO("Moto", "Moto", "Blue", null);
        CategoryDTO cat3 = new CategoryDTO("Car", "Tesla", "#fff", null);
        CategoryDTO cat4 = new CategoryDTO("TV", "Smart TV", "#e55edd", null);
        CategoryDTO cat5 = new CategoryDTO("Tablet", "I-Pad", "#e55edd", null);
        List<Category> categoryLists = Stream.of(cat1, cat2, cat3, cat4, cat5).map(p -> this.categoryMapper.toEntity(p, categoryRepository)).toList();
        try {
            categoryRepository.saveAll(categoryLists);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private void seedProduct() {
        log.info("seeding products . . . . . . . . . .");
        ProductRequest p1 = new ProductRequest("I-Phone 10 Pro Max", "color Gold",new BigDecimal(1200),new BigDecimal(1400) ,1L, "#fafaee", "", "IMEI0000001", "IMEI0000002", null, null);
        ProductRequest p2 = new ProductRequest("I-Phone 11 Pro Max", "color Gold",new BigDecimal(1200),new BigDecimal(1400), 1L, "#fafaee", "", "IMEI0000003", "IMEI0000004", null, null);
        ProductRequest p3 = new ProductRequest("Honda Dream 2025", "Black",new BigDecimal(2200),new BigDecimal(2600), 2L, "#fafaee", "", "1BL-5522", "02400012555", null, null);
        ProductRequest p4 = new ProductRequest("SCOOPY 2025", "White",new BigDecimal(2400),new BigDecimal(2900), 2L, "#fafaee", "", "1FL-2255", "012554455", null, null);
        ProductRequest p5 = new ProductRequest("Cyber Truck 2024", "Black",new BigDecimal(70000),new BigDecimal(100000), 3L, "#fafaee", "", "1FF-MENGLANG", "012225544", null, null);
        ProductRequest p6 = new ProductRequest("Samsung TV", "Online TV",new BigDecimal(200),new BigDecimal(300), 4L, "#fafaee", "", "TV-MENGLANG", "000011112", null, null);
        List<Product> products = Stream.of(p1, p2, p3, p4, p5, p6).map(this.productMapper::toEntity).toList();
        try {
            productRepository.saveAll(products);
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
