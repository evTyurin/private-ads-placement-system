package com.senlainc.warsaw.tyurin;

import com.senlainc.warsaw.tyurin.entity.User;

import java.util.Optional;

public interface IUserRepository {

    Optional<User> findById(long id);

    void create(User user);

    void update(User user);

    void deleteById(long id);

    void delete(User user);

    Optional<User> findByEmail(String name);
}