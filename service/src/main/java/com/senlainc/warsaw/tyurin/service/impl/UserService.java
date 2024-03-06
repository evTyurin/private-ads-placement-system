package com.senlainc.warsaw.tyurin.service.impl;

import com.senlainc.warsaw.tyurin.IUserRepository;
import com.senlainc.warsaw.tyurin.constants.ExceptionCode;
import com.senlainc.warsaw.tyurin.entity.Role;
import com.senlainc.warsaw.tyurin.entity.User;
import com.senlainc.warsaw.tyurin.enums.StandardRole;
import com.senlainc.warsaw.tyurin.service.IRoleService;
import com.senlainc.warsaw.tyurin.service.IUserService;
import com.senlainc.warsaw.tyurin.service.exception.EntityExistException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByNameException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class UserService implements IUserService {

    private IUserRepository userRepository;
    private IRoleService roleService;
    private PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository,
                       IRoleService roleService,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User find(long id) throws NotFoundByIdException {
        return userRepository.findById(id)
                .orElseThrow(()
                        -> new NotFoundByIdException(id, ExceptionCode.NOT_FOUND_EXCEPTION_CODE));
    }

    @Override
    public User find(String email) throws NotFoundByNameException {
        return userRepository.findByEmail(email).orElseThrow(()
                -> new NotFoundByNameException(email, ExceptionCode.NOT_FOUND_EXCEPTION_CODE));
    }

    @Override
    public void create(User user) throws NotFoundByIdException, NotFoundByNameException, EntityExistException {
        Optional<User> existUser = userRepository.findByEmail(user.getEmail());
        if (existUser.isPresent()) {
            throw new EntityExistException(existUser.get().getId(), HttpStatus.CONFLICT.value());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Stream.of(roleService.find(StandardRole.USER.name())).collect(Collectors.toList()));
        userRepository.create(user);
    }

    @Override
    public void update(long id, User updatedUser) throws NotFoundByIdException, EntityExistException {
        User user = find(id);
        String newPassword = updatedUser.getPassword();
        if (!(updatedUser.getEmail().equals(user.getEmail()) || !userRepository.findByEmail(updatedUser.getEmail()).isPresent())) {
            throw new EntityExistException(id, HttpStatus.CONFLICT.value());
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setEmail(updatedUser.getEmail());
        user.setName(updatedUser.getName());
        user.setSurname(updatedUser.getSurname());
        userRepository.update(user);
    }

    @Override
    public void delete(long id) throws NotFoundByIdException {
        User user = find(id);
        userRepository.delete(user);
    }

    @Transactional
    @Override
    public String createTemporaryPassword(long id, String resetPasswordToken) throws NotFoundByIdException {
        User user = find(id);
        String temporaryPassword = RandomString.make(15);
        if (user.getResetPasswordToken().equals(resetPasswordToken)) {
            user.setResetPasswordToken(null);
            user.setPassword(passwordEncoder.encode(temporaryPassword));
            userRepository.update(user);
        }
        return temporaryPassword;
    }

    @Transactional
    @Override
    public void addAdminRole(long id) throws NotFoundByIdException, NotFoundByNameException {
        User user = find(id);
        Role adminRole = roleService.find("ADMIN");
        user.getRoles().add(adminRole);
        userRepository.update(user);
    }

    @Override
    public void deleteAdminRole(long id) throws NotFoundByIdException, NotFoundByNameException {
        User user = find(id);
        Role adminRole = roleService.find("ADMIN");
        user.setRoles(user
                .getRoles()
                .stream()
                .filter(role -> role.getId() != adminRole.getId())
                .collect(Collectors.toList()));
        userRepository.update(user);
    }

    @SneakyThrows
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = find(username);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }
}
