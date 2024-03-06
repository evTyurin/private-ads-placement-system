package com.senlainc.warsaw.tyurin.service;

import com.senlainc.warsaw.tyurin.IRoleRepository;
import com.senlainc.warsaw.tyurin.entity.Role;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByNameException;
import com.senlainc.warsaw.tyurin.service.impl.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;
    @Mock
    private IRoleRepository roleRepository;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(roleRepository);
    }
    private Role expectedRole;

    @BeforeEach
    void init() {
        expectedRole = new Role();
        expectedRole.setId(1);
        expectedRole.setName("USER");
    }

    @Test
    void find_findRoleByCorrectName_findRole() throws NotFoundByNameException {
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(expectedRole));

        Role actualRole = roleService.find("USER");
        assertEquals(expectedRole, actualRole);
    }
}
