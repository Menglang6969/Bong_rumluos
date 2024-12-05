package com.menglang.bong_rumluos.Bong_rumluos.dto.loan;

import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.loanDetails.LoanDetailMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.loanDetails.LoanDetailsResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Customer;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Loan;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanDetails;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CustomerRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",uses = {LoanDetailMapper.class, CustomerMapper.class})
public interface LoanMapper {
    LoanMapper INSTANCE= Mappers.getMapper(LoanMapper.class);
    Logger log = LoggerFactory.getLogger(LoanMapper.class);

    @Mapping(target = "customer",source = "customer_id",qualifiedByName = "mapCustomer")
    @Mapping(target = "loanDetails",ignore = true)
    @Mapping(target = "totalAmount",ignore = true)
    @Mapping(target = "totalInterest",ignore = true)
    @Mapping(target = "loanKey",ignore = true)
    Loan toLoan(LoanDto loanDto, @Context CustomerRepository customerRepository);

    @Named("mapCustomer")
    default Customer mapCustomer(Long cid,@Context CustomerRepository customerRepository){
        return customerRepository.findById(cid).orElseThrow(()->new NotFoundException("Customer Not Found"));

    }

    @Mapping(target = "loanDetails",source = "loanDetails",qualifiedByName = "mapLoanDetails")
    @Mapping(target = "customer",source = "customer",qualifiedByName = "mapToCustomerResponse")
    LoanResponse toLoanResponse(Loan loan);

    LoanDetailsResponse toLoanDetails(LoanDetails loanDetail);

    CustomerResponse toCustomer(Customer customer);

    @Named("mapLoanDetails")
    default List<LoanDetailsResponse> mapLoanDetails(Set<LoanDetails> loanDetails){
        List<LoanDetailsResponse> loanDetailsResponses=new ArrayList<>();
        for (LoanDetails ld:loanDetails){
            LoanDetailsResponse loanDetailsResponse=this.toLoanDetails(ld);
            loanDetailsResponses.add(loanDetailsResponse);
        }
        return loanDetailsResponses;
    }

    @Named("mapToCustomerResponse")
    default CustomerResponse mapToCustomerResponse(Customer customer){
        log.info("customer info:{} ",customer.getName());
        return this.toCustomer(customer);
    }
}
