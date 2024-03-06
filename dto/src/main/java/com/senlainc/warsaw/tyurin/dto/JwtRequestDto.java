package com.senlainc.warsaw.tyurin.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class JwtRequestDto {
    @Size(min = 3, max = 1000, message = "description.size.message")
    @NotNull(message = "null.field.message")
    private String email;
    @Size(min = 3, max = 1000, message = "description.size.message")
    @NotNull(message = "null.field.message")
    private String password;
}
