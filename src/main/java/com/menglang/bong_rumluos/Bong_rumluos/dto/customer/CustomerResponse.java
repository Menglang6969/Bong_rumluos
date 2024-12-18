package com.menglang.bong_rumluos.Bong_rumluos.dto.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.menglang.bong_rumluos.Bong_rumluos.dto.BaseAuditDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonPropertyOrder({"id", "name", "phone", "address", "occupation", "imageUrl", "docsUrls", "createdAt", "createdBy", "updatedAt", "updatedBy"})
public class CustomerResponse extends BaseAuditDTO {
    private String name;
    private String phone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String occupation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String imageUrl;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> docsUrls;


    public CustomerResponse(Long id,LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(id,createdAt, createdBy, updatedAt, updatedBy);
    }
}
