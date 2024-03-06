package com.senlainc.warsaw.tyurin.impl;

import com.senlainc.warsaw.tyurin.IRoleRepository;
import com.senlainc.warsaw.tyurin.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class RoleRepository extends AbstractRepository<Role> implements IRoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Role> findByName(String name) {
        final String FIND_ROLE_BY_NAME = "SELECT role FROM Role role WHERE role.name=:name";
        TypedQuery<Role> query = entityManager.createQuery(FIND_ROLE_BY_NAME, Role.class);
        query.setParameter("name", name);
        return query.getResultStream().findFirst();
    }
}
