package com.senlainc.warsaw.tyurin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class RoleDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    @Size(min = 3, max = 1000, message = "description.size.message")
    @NotNull(message = "null.field.message")
    private String name;
}