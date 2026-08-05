package com.marketingtrade.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductTypeRequest(

        @NotNull
        Long productId,

        @NotBlank
        String typeName,

        String specification,

        @NotBlank
        String unit,

        Boolean active
) {
}