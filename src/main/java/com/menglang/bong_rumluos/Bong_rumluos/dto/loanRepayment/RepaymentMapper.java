package com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Invoice;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanDetails;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanRepayment;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.InvoiceRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanDetailsRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RepaymentMapper {
    RepaymentMapper INSTANCE = Mappers.getMapper(RepaymentMapper.class);

    @Mapping(target = "loanDetails", source = "loanDetails", qualifiedByName = "mapLoanDetail")
//    @Mapping(target = "invoiceId",source = "invoiceId",qualifiedByName = "mapInvoiceId")
    @Mapping(target = "invoiceId",ignore = true)
    @Mapping(target = "penalty",ignore = true)
    LoanRepayment toLoanRepayment(RepaymentRequestDTO repaymentRequestDTO, @Context LoanDetailsRepository loanDetailsRepository, @Context InvoiceRepository invoiceRepository);

    @Mapping(target = "invoiceNo",source = "invoiceId.invoiceNo")
    RepaymentResponseDTO toRepaymentResponse(LoanRepayment loanRepayment);

    @Named("mapLoanDetail")
    default LoanDetails mapLoanDetail(Long loanDetailsId, @Context LoanDetailsRepository loanDetailsRepository,@Context InvoiceRepository invoiceRepository) {
        return loanDetailsRepository.findById(loanDetailsId).orElseThrow(() -> new NotFoundException("Not Found Loan Details"));
    }
    @Named("mapInvoiceId")
    default Invoice mapInvoiceId(Long invoiceId, @Context LoanDetailsRepository loanDetailsRepository,@Context InvoiceRepository invoiceRepository) {
        return invoiceRepository.findById(invoiceId).orElseThrow(() -> new NotFoundException("Invoice Not Found..."));
    }

//    @Named("mapLoanDetailToInvoice")
//    default String mapLoanDetailToInvoice()
}
