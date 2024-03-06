package com.senlainc.warsaw.tyurin.impl;

import com.senlainc.warsaw.tyurin.IAbstractRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

@Repository
public abstract class AbstractRepository<T> implements IAbstractRepository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> entityClass;

    protected AbstractRepository() {
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Transactional
    @Override
    public Optional<T> findById(long id) {
        return Optional.ofNullable(entityManager.find(this.entityClass, id));
    }

    @Transactional
    @Override
    public void create(final T object) {
        entityManager.persist(object);
    }

    @Transactional
    @Override
    public void update(final T object) {
        entityManager.merge(object);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        entityManager.remove(entityManager.find(this.entityClass, id));
    }

    @Transactional
    @Override
    public void delete(T object) {
        entityManager.remove(object);
    }
}
