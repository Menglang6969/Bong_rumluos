package com.menglang.bong_rumluos.Bong_rumluos.dto.loan;

import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerBaseResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.loanDetails.LoanDetailMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.loanDetails.LoanDetailsResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Customer;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Loan;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanDetails;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Product;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CustomerRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.ProductRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Mapper(componentModel = "spring", uses = {LoanDetailMapper.class, CustomerMapper.class})
public interface LoanMapper {
    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);
    Logger log = LoggerFactory.getLogger(LoanMapper.class);

    @Mapping(target = "customer", source = "customer_id", qualifiedByName = "mapCustomer")
    @Mapping(target = "products", source = "products", qualifiedByName = "mapProduct")
    @Mapping(target = "loanDetails", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "totalInterest", ignore = true)
    @Mapping(target = "loanKey", ignore = true)
    @Mapping(target = "loanStatus",ignore = true)
    Loan toLoan(LoanDto loanDto, @Context CustomerRepository customerRepository, @Context ProductRepository productRepository);

    @Named("mapCustomer")
    default Customer mapCustomer(Long cid, @Context CustomerRepository customerRepository, @Context ProductRepository productRepository) {
        log.info(" customer {}",cid);
        return customerRepository.findById(cid).orElseThrow(() -> new NotFoundException("Customer Not Found"));

    }

    @Named("mapProduct")
    default Set<Product> mapProduct(List<Long> products, @Context CustomerRepository customerRepository, @Context ProductRepository productRepository) {
        Set<Product> setProducts=new HashSet<>();
        for (Long pid:products){
            Product product= productRepository.findById(pid).orElseThrow(() -> new NotFoundException("Product Not Found "+pid));
            setProducts.add(product);
        }
        return setProducts;
    }

    @Mapping(target = "loanDetails", source = "loanDetails", qualifiedByName = "mapLoanDetails")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "mapToCustomerResponse")
    LoanResponse toLoanResponse(Loan loan);

    @Mapping(target = "isPaymentComing",source = "repaymentDate",qualifiedByName = "checkIsComingDate")
    LoanDetailsResponse toLoanDetails(LoanDetails loanDetail);

    CustomerBaseResponse toCustomer(Customer customer);

    @Named("mapLoanDetails")
    default List<LoanDetailsResponse> mapLoanDetails(Set<LoanDetails> loanDetails) {
        List<LoanDetailsResponse> loanDetailsResponses = new ArrayList<>();

        // Map LoanDetails to LoanDetailsResponse
        for (LoanDetails ld : loanDetails) {
            LoanDetailsResponse loanDetailsResponse = this.toLoanDetails(ld);
            loanDetailsResponses.add(loanDetailsResponse);
        }

        // Sort the list in place
        loanDetailsResponses.sort(Comparator.comparing(LoanDetailsResponse::getRepaymentDate));

        return loanDetailsResponses;
    }

    @Named("mapToCustomerResponse")
    default CustomerBaseResponse mapToCustomerResponse(Customer customer) {
        log.info("customer info:{} ", customer.getName());
        return this.toCustomer(customer);
    }

}
