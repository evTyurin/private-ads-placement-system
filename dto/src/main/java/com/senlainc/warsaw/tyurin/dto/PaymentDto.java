package com.senlainc.warsaw.tyurin.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PaymentDto {
    @Min(value = 1, message = "not.positive.id.message")
    @NotNull(message = "null.field.message")
    private Long payerId;
    @Min(value = 1, message = "not.positive.id.message")
    @NotNull(message = "null.field.message")
    private Long advertisementId;
    @Min(value = 100, message = "not.positive.duration.message")
    @Max(value = 100, message = "not.positive.duration.message")
    @NotNull(message = "null.field.message")
    private Double paidSum;
}

