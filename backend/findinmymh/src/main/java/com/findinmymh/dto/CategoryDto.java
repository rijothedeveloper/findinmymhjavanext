package com.findinmymh.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDto(
        @NotBlank(message = "email is mandatory")
        @Size(min=6, max=30, message = "min 6 and max 30")
        String name
) {
}
