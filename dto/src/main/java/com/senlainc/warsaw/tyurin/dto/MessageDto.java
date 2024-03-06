package com.senlainc.warsaw.tyurin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
public class MessageDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    @Min(value = 1, message = "not.positive.id.message")
    @NotNull(message = "null.field.message")
    private Long senderId;
    @Min(value = 1, message = "not.positive.id.message")
    @NotNull(message = "null.field.message")
    private Long receiverId;
    @Size(min = 3, max = 1000, message = "description.size.message")
    @NotNull(message = "null.field.message")
    private String message;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creationDateTime;
}
