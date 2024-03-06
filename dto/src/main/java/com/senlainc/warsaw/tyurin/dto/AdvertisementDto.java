package com.senlainc.warsaw.tyurin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import com.senlainc.warsaw.tyurin.enums.AdvertisementStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AdvertisementDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long customerId;
    @Min(value = 1, message = "not.positive.id.message")
    @NotNull(message = "null.field.message")
    private Long sellerId;
    @Size(min = 3, max = 1000, message = "description.size.message")
    @NotNull(message = "null.field.message")
    private String title;
    @Size(min = 3, max = 1000, message = "description.size.message")
    @NotNull(message = "null.field.message")
    private String description;
    @Min(value = 0, message = "not.positive.duration.message")
    @NotNull(message = "null.field.message")
    private Double price;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AdvertisementStatus status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creationDateTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isPaid;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime paidTillDateTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double paidSum;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime soldDateTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double soldSum;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Valid
    private List<CommentDto> comments;
}
