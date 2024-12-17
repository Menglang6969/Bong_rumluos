package com.menglang.bong_rumluos.Bong_rumluos.services.customer;

import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerRequest;
import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerResponse;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerRequest customerRequest) throws BadRequestException;
    CustomerResponse view(Long id) throws BadRequestException;
    CustomerResponse update(Long id,CustomerRequest customerRequest) throws BadRequestException;
    CustomerResponse delete(Long id) throws BadRequestException;
    List<CustomerResponse> getCustomers(int page,int limit,String orderBy,String sortBy,String query) throws BadRequestException;

}
