package com.menglang.bong_rumluos.Bong_rumluos.dto.invoice;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentRequestDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentResponseDTO;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Customer;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Invoice;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanDetails;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanRepayment;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CustomerRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.InvoiceRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanDetailsRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {RepaymentMapper.class})
public interface InvoiceMapper {
    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    @Mapping(target = "loanRepayments",source = "loanRepayments",qualifiedByName = "mapRepaymentsResponse")
    InvoiceResponseDTO toResponse(Invoice invoice);

    @Mapping(target = "customer", source = "customer", qualifiedByName = "mapToCustomer")
    @Mapping(target = "invoiceNo", ignore = true)
    @Mapping(target = "totalAmount",ignore = true)
    @Mapping(target = "loanRepayments",ignore = true)
    @Mapping(target = "totalPenalty",ignore = true)
    @Mapping(target = "totalPayment",ignore = true)
    Invoice toInvoice(InvoiceRequestDTO invoiceRequestDTO, @Context CustomerRepository customerRepository, @Context LoanDetailsRepository loanDetailsRepository, @Context InvoiceRepository invoiceRepository);

    @Mapping(target = "loanDetails", source = "loanDetails", qualifiedByName = "mapLoanDetails")
    @Mapping(target = "invoiceId",ignore = true)
    @Mapping(target = "penalty",ignore = true)
    LoanRepayment toLoanRepayment(RepaymentRequestDTO repaymentRequestDTO, @Context LoanDetailsRepository loanDetailsRepository,@Context InvoiceRepository invoiceRepository);

    @Mapping(target = "invoiceNo",source = "invoiceId.invoiceNo")
//    @Mapping(target = "loanDetails",source = "loanDetails",qualifiedByName = "mapLoanDetails")
//    @Mapping(target = "loanDetails",ignore = true)
    RepaymentResponseDTO toRepaymentResponseDTO(LoanRepayment loanRepayment);


    @Mapping(target = "loanRepayments",ignore = true)
    @Mapping(target = "customer",source = "customer",qualifiedByName = "mapToCustomer")
    @Mapping(target = "totalPayment",source = "totalAmount")
    Invoice toInvoiceHalf(InvoiceHalfPayRequestDTO requestDTO,@Context CustomerRepository customerRepository, @Context LoanDetailsRepository loanDetailsRepository, @Context InvoiceRepository invoiceRepository);

    @Named("mapLoanRepayments")
    default LoanRepayment mapLoanRepayments(
            RepaymentRequestDTO repaymentRequestDTO,
            @Context CustomerRepository customerRepository,
            @Context LoanDetailsRepository loanDetailsRepository,
            @Context InvoiceRepository invoiceRepository
            ) {
            return toLoanRepayment(repaymentRequestDTO,loanDetailsRepository,invoiceRepository);
    }

    @Named("mapToCustomer")
    default Customer mapToCustomer(Long customerID, @Context CustomerRepository customerRepository,@Context LoanDetailsRepository loanDetailsRepository, @Context InvoiceRepository invoiceRepository) {
        return customerRepository.findById(customerID).orElseThrow(() -> new NotFoundException("Customer Not Found"));
    }

    @Named("mapRepaymentsResponse")
    default List<RepaymentResponseDTO> mapRepaymentsResponse(Set<LoanRepayment> loanRepayments){
        return loanRepayments.stream().map(this::toRepaymentResponseDTO).toList();
    }

    @Named("mapLoanDetails")
    default LoanDetails mapLoanDetails(Long loanDetailsId, @Context LoanDetailsRepository loanDetailsRepository, @Context InvoiceRepository invoiceRepository) {
        return loanDetailsRepository.findById(loanDetailsId).orElseThrow(() -> new NotFoundException("Not Found Loan Details"));
    }

}
