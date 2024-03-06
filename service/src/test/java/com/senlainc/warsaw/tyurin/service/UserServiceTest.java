package com.senlainc.warsaw.tyurin.service;

import com.senlainc.warsaw.tyurin.IUserRepository;
import com.senlainc.warsaw.tyurin.entity.Role;
import com.senlainc.warsaw.tyurin.entity.User;
import com.senlainc.warsaw.tyurin.enums.StandardRole;
import com.senlainc.warsaw.tyurin.service.exception.EntityExistException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByNameException;
import com.senlainc.warsaw.tyurin.service.impl.RoleService;
import com.senlainc.warsaw.tyurin.service.impl.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private RoleService roleService;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private User testUser;
    private User updatedUser;
    private Role roleAdmin;
    private Role roleUser;

    @BeforeEach
    void init() {
        roleAdmin = new Role();
        roleAdmin.setId(1);
        roleAdmin.setName("ADMIN");

        roleUser = new Role();
        roleUser.setId(1);
        roleUser.setName("USER");

        testUser = new User();
        testUser.setId(1);
        testUser.setPassword("123456");
        testUser.setEmail("123456@gmail.com");
        testUser.setRoles(Stream.of(roleUser).collect(Collectors.toList()));

        updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setPassword("123456");
        updatedUser.setEmail("123456@gmail.com");
        updatedUser.setRoles(Stream.of(roleUser).collect(Collectors.toList()));
    }

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(userRepository, roleService);
    }

    @Test
    void find_findUserByCorrectId_returnUser() throws NotFoundByIdException {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        User actualUser = userRepository.findById(1).orElseThrow(() -> new NotFoundByIdException(1, 404));
        assertEquals(testUser, actualUser);
    }

    @Test
    void find_findUserByIncorrectId_throwException() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundByIdException.class, () -> userService.find(1));
    }

    @Test
    void delete_deleteUserByCorrectId_deleteUser() throws NotFoundByIdException {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        userService.delete(1);

        verify(userRepository).delete(testUser);
    }

    @Test
    void delete_deleteUserByIncorrectId_throwException() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundByIdException.class, () -> userService.delete(1));
    }

    @Test
    void create_createUser_createSuccessfully() throws NotFoundByIdException, EntityExistException, NotFoundByNameException {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());
        when(roleService.find(StandardRole.USER.name())).thenReturn(roleUser);
        when(passwordEncoder.encode(testUser.getPassword())).thenReturn("123456");

        userService.create(testUser);

        verify(userRepository).create(testUser);
        verify(roleService).find(StandardRole.USER.name());
        verify(passwordEncoder).encode(testUser.getPassword());
    }

    @Test
    void create_updateUser_updateSuccessfully() throws NotFoundByIdException, EntityExistException {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        String newPassword = updatedUser.getPassword();
        testUser.setPassword(passwordEncoder.encode(newPassword));

        userService.update(1,testUser);
        verify(userRepository).update(testUser);
        verify(passwordEncoder).encode(newPassword);
    }

    @Test
    void create_addAdminRole_addSuccessfully() throws NotFoundByIdException, EntityExistException, NotFoundByNameException {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(roleService.find("ADMIN")).thenReturn(roleAdmin);

        Role role = roleService.find("ADMIN");
        testUser.setRoles(Stream.of(role).collect(Collectors.toList()));

        userService.update(1,testUser);
        verify(userRepository).update(testUser);
        verify(roleService).find("ADMIN");
    }

    @Test
    void create_deleteAdminRole_deletedSuccessfully() throws NotFoundByIdException, EntityExistException, NotFoundByNameException {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(roleService.find("ADMIN")).thenReturn(roleAdmin);

        Role adminRole = roleService.find("ADMIN");
        testUser.setRoles(testUser
                .getRoles()
                .stream()
                .filter(role -> role.getId() != adminRole.getId())
                .collect(Collectors.toList()));

        userService.update(1,testUser);
        verify(userRepository).update(testUser);
        verify(roleService).find("ADMIN");
    }
}
