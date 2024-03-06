package com.senlainc.warsaw.tyurin.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class MessageSearchDto {
    @Min(value = 1, message = "not.positive.id.message")
    @NotNull(message = "null.field.message")
    private Long senderId;
    @Min(value = 1, message = "not.positive.id.message")
    @NotNull(message = "null.field.message")
    private Long receiverId;
}
