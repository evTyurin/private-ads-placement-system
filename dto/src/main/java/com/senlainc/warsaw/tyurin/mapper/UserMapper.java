package com.senlainc.warsaw.tyurin.mapper;

import com.senlainc.warsaw.tyurin.dto.UserDto;
import com.senlainc.warsaw.tyurin.entity.Role;
import com.senlainc.warsaw.tyurin.entity.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public User mapToEntity(UserDto dto) {
        return User
                .builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .creationDateTime(LocalDateTime.now())
                .build();
    }

    public UserDto mapToDto(User user) {
        List<String> roles = user
                .getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return UserDto
                .builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .balance(user.getBalance())
                .rating(user.getRating())
                .role(roles)
                .creationDateTime(user.getCreationDateTime())
                .build();
    }
}

