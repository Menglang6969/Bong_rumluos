package com.menglang.bong_rumluos.Bong_rumluos.dto.loan.loanDetails;

import com.menglang.bong_rumluos.Bong_rumluos.entities.Loan;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Mapper(componentModel = "spring")
public interface LoanDetailMapper {
    LoanDetailMapper INSTANCE= Mappers.getMapper(LoanDetailMapper.class);

    @Mapping(target = "isPaymentComing",source = "repaymentDate",qualifiedByName = "checkIsComingDate")
    LoanDetailsResponse toLoanResponse(LoanDetails loanDetails);

    @Mapping(target = "loan",source = "loan",qualifiedByName = "mapLoan")
    LoanDetails toLoanDetailsDTO(LoanDetailsRequest loanDetailsRequest);

    @Named("mapLoan")
    public default Loan mapLoan(Long loanId){
        Loan loan=new Loan();
        loan.setId(loanId);
        return loan;
    }

    @Named("checkIsComingDate")
    default boolean checkIsComingDate(LocalDate repaymentDate){
        LocalDate today = LocalDate.now();
        int isComingDate = (int) ChronoUnit.DAYS.between(today, repaymentDate);
        return isComingDate <= 3;//filter date before 3days or after
    }
}
