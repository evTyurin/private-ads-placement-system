package com.senlainc.warsaw.tyurin.service.impl;

import com.senlainc.warsaw.tyurin.IRoleRepository;
import com.senlainc.warsaw.tyurin.constants.ExceptionCode;
import com.senlainc.warsaw.tyurin.entity.Role;
import com.senlainc.warsaw.tyurin.service.IRoleService;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByNameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleService implements IRoleService {

    private IRoleRepository roleDao;

    public RoleService(IRoleRepository roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role find(long id) throws NotFoundByIdException {
        return roleDao.findById(id)
                .orElseThrow(()
                        -> new NotFoundByIdException(id, ExceptionCode.NOT_FOUND_EXCEPTION_CODE));
    }

    @Override
    public Role find(String name) throws NotFoundByNameException {
        return roleDao.findByName(name).orElseThrow(()
                -> new NotFoundByNameException(name, ExceptionCode.NOT_FOUND_EXCEPTION_CODE));
    }
}
