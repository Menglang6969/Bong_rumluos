package com.menglang.bong_rumluos.Bong_rumluos.services.customer;
import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerRequest;
import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Customer;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) throws BadRequestException {
        Customer customer = customerMapper.toEntity(customerRequest);
        try {
            return customerMapper.toCustomer(customerRepository.save(customer));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public CustomerResponse view(Long id) throws BadRequestException {
        return customerMapper.toCustomer(this.findCustomerById(id));
    }

    @Override
    public CustomerResponse update(Long id, CustomerRequest customerRequest) throws BadRequestException {
        try {
            Customer existCustomer = findCustomerById(id);
            customerMapper.updateCustomerDtoToEntity(customerRequest, existCustomer);
            Customer updatedCustomer = customerRepository.save(existCustomer);
            return customerMapper.toCustomer(updatedCustomer);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public CustomerResponse delete(Long id) throws BadRequestException {
        Customer customer = findCustomerById(id);
        try {
            customerRepository.delete(customer);
            return customerMapper.toCustomer(customer);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<CustomerResponse> getCustomers(int page, int limit, String orderBy, String sortBy, String query) throws BadRequestException {
        Sort sort = orderBy.equals("ASC") ? Sort.by(Sort.Direction.ASC, sortBy) : Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page-1, limit, sort);
        Page<Customer> customers = customerRepository.findAllByNameOrPhone(query,pageable);
        log.info("customer name: {}",customers.getContent().get(0).getName());
        return customers.getContent().stream().map(this.customerMapper::toCustomer).toList();
    }

    private Customer findCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer Not Found"));
    }

}
