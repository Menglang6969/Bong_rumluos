package com.menglang.bong_rumluos.Bong_rumluos.dto.product;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated
public record ProductRequest(
        @Nonnull
        @NotBlank(message = "Name Must be not Blank")
        @Size(min = 3,message = "Product Name have at lease 3 Characters")
        String name,
        String description,
        @Nonnull
        @NotBlank(message = "Category Must be Not Blank")
        Long categoryId,
        String color,
        String imageUrl,
        @Nullable
        String identify1,
        @Nullable
        String identify2,
        @Nullable
        String identify3,
        @Nullable
        String identify4
) {
}
