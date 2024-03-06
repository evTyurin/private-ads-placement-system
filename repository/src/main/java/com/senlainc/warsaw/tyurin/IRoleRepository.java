package com.senlainc.warsaw.tyurin;

import com.senlainc.warsaw.tyurin.entity.Role;

import java.util.Optional;

public interface IRoleRepository {

    Optional<Role> findById(long id);

    void create(Role role);

    void update(Role role);

    void deleteById(long id);

    Optional<Role> findByName(String name);
}