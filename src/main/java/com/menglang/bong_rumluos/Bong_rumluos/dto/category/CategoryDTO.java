package com.menglang.bong_rumluos.Bong_rumluos.dto.category;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    @Nonnull
    @NotBlank(message = "Name must be not null")
    @Size(min = 5)
    private String name;
    private String description;
    private String color;
    @Nullable
    private Long parentId;
}
