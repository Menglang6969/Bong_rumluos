package com.menglang.bong_rumluos.Bong_rumluos.dto.loan;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",uses = {LoanDetailMapper.class})
public interface LoanMapper {
    LoanMapper INSTANCE= Mappers.getMapper(LoanMapper.class);

    @Mapping(target = "customer",source = "customer_id",qualifiedByName = "mapCustomer")
    @Mapping(target = "loanDetails",ignore = true)
    @Mapping(target = "totalAmount",ignore = true)
    @Mapping(target = "totalInterest",ignore = true)
    Loan toLoanDTO(LoanDto loanDto, @Context CustomerRepository customerRepository);

    @Named("mapCustomer")
    default Customer mapCustomer(Long cid,@Context CustomerRepository customerRepository){
        customerRepository.findById(cid).orElseThrow(()->new NotFoundException("Customer Not Found"));
        Customer cus=new Customer();
        cus.setId(cid);
        return cus;
    }

    @Mapping(target = "loanDetails",source = "loanDetails",qualifiedByName = "mapLoanDetails")
    LoanResponse toLoan(Loan loan);

    LoanDetailsResponse toLoanDetails(LoanDetails loanDetail);

    @Named("mapLoanDetails")
    default List<LoanDetailsResponse> mapLoanDetails(Set<LoanDetails> loanDetails){
        List<LoanDetailsResponse> loanDetailsResponses=new ArrayList<>();
        for (LoanDetails ld:loanDetails){
            LoanDetailsResponse loanDetailsResponse=this.toLoanDetails(ld);
            loanDetailsResponses.add(loanDetailsResponse);
        }
        return loanDetailsResponses;
    }
}
