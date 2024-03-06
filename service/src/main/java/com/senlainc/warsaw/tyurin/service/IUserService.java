package com.senlainc.warsaw.tyurin.service;

import com.senlainc.warsaw.tyurin.entity.User;
import com.senlainc.warsaw.tyurin.service.exception.EntityExistException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByNameException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    User find(long id) throws NotFoundByIdException;

    User find(String email) throws NotFoundByNameException;

    void create(User user) throws NotFoundByIdException, NotFoundByNameException, EntityExistException;

    void update(long id, User user) throws NotFoundByIdException, EntityExistException;

    void delete(long id) throws NotFoundByIdException;

    String createTemporaryPassword(long id, String resetPassword) throws NotFoundByIdException;

    void addAdminRole(long id) throws NotFoundByIdException, NotFoundByNameException;

    void deleteAdminRole(long id) throws NotFoundByIdException, NotFoundByNameException;
}