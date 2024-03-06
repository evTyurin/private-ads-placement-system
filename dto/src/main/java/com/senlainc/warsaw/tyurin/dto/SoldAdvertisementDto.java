package com.senlainc.warsaw.tyurin.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SoldAdvertisementDto {

    @Min(value = 1, message = "not.positive.id.message")
    @NotNull(message = "null.field.message")
    private Long customerId;
    @Min(value = 1, message = "not.positive.id.message")
    @NotNull(message = "null.field.message")
    private Long advertisementId;
    @Min(value = 0, message = "not.positive.duration.message")
    @NotNull(message = "null.field.message")
    private Double soldSum;
}
