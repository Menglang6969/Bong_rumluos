package com.menglang.bong_rumluos.Bong_rumluos.controllers;

import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerRequest;
import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerResponse;
import com.menglang.bong_rumluos.Bong_rumluos.services.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest customerRequest){
        return ResponseEntity.ok(customerService.create(customerRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> view(@PathVariable("id") Long id){
        return ResponseEntity.ok(customerService.view(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable("id") Long id,@Valid @RequestBody CustomerRequest customerRequest){
        return ResponseEntity.ok(customerService.update(id,customerRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponse> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(customerService.delete(id));
    }

    @GetMapping("get-all")
    public ResponseEntity<List<CustomerResponse>> getCustomers(
        @RequestParam(name = "page",defaultValue = "1") int page,
        @RequestParam(name = "limit",defaultValue = "10") int limit,
        @RequestParam(name = "orderBy",defaultValue = "DESC") String orderBy,
        @RequestParam(name = "sortBy",defaultValue = "name") String sortBy,
        @RequestParam(name = "query",defaultValue = "") String query
    ){
        return ResponseEntity.ok(customerService.getCustomers(page,limit,orderBy,sortBy,query));
    }

}
