package com.menglang.bong_rumluos.Bong_rumluos.services.customer;

import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerRequest;
import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Customer;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerRequest customerRequest) throws BadRequestException;
    CustomerResponse view(Long id) throws BadRequestException;
    CustomerResponse update(Long id,CustomerRequest customerRequest) throws BadRequestException;
    CustomerResponse delete(Long id) throws BadRequestException;
    Page<Customer> getCustomers(int page, int limit, String orderBy, String sortBy, String query) throws BadRequestException;

}
