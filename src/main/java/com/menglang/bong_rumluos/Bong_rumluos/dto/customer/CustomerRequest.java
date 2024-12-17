package com.menglang.bong_rumluos.Bong_rumluos.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Builder
public record CustomerRequest(
        @NotBlank(message = "Customer Name must be not blank")
        @NotNull
        @Size(min = 2)
        String name,
        @NotBlank(message = "Customer Phone Name must be not blank")
        @NotNull
        @Size(min = 9, max = 13, message = "Phone length is between 9 and 13 characters")
        String phone,
        @NotBlank(message = "Customer Address must be not blank")
        @NotNull
        @Size(min = 5, max = 150, message = "Phone length is between 9 and 13 characters")
        String address,
        String occupation,
        String imageUrl,
        @NotNull
        @NotEmpty(message = "Customer Docs must be not null")
        List<String> docsUrls
) {
}
