package com.senlainc.warsaw.tyurin.impl;

import com.senlainc.warsaw.tyurin.IUserRepository;
import com.senlainc.warsaw.tyurin.entity.User;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserRepository extends AbstractRepository<User> implements IUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findByEmail(String email) {
        final String FIND_USER_BY_EMAIL = "SELECT user FROM User user WHERE user.email=:email";
        TypedQuery<User> query = entityManager.createQuery(FIND_USER_BY_EMAIL, User.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst();
    }
}