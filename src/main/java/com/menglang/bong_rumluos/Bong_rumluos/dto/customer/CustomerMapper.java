package com.menglang.bong_rumluos.Bong_rumluos.dto.customer;

import com.menglang.bong_rumluos.Bong_rumluos.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer toEntity(CustomerRequest customerRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateCustomerDtoToEntity(CustomerRequest customerRequest, @MappingTarget Customer customer);

    CustomerResponse toCustomer(Customer customer);


}
