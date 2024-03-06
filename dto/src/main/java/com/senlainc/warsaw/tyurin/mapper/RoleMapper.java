package com.senlainc.warsaw.tyurin.mapper;

import com.senlainc.warsaw.tyurin.dto.RoleDto;
import com.senlainc.warsaw.tyurin.entity.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleMapper {

    public Role mapToEntity(RoleDto dto) {
        return Role
                .builder()
                .name(dto.getName())
                .build();
    }
}
