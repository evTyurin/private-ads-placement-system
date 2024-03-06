package com.senlainc.warsaw.tyurin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    @Size(min = 3, max = 1000, message = "description.size.message")
    @NotNull(message = "null.field.message")
    private String name;
    @Size(min = 3, max = 1000, message = "description.size.message")
    @NotNull(message = "null.field.message")
    private String surname;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> role;
    @Size(min = 3, max = 1000, message = "description.size.message")
    @NotNull(message = "null.field.message")
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 3, max = 1000, message = "description.size.message")
    @NotNull(message = "null.field.message")
    private String password;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double balance;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double rating;
    @JsonIgnore
    private String resetPasswordToken;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creationDateTime;
}
