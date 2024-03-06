package com.senlainc.warsaw.tyurin;

import java.util.Optional;

public interface IAbstractRepository<T> {

    void create(T object);

    Optional<T> findById(long id);

    void update(T object);

    void deleteById(long id);

    void delete(T object);
}