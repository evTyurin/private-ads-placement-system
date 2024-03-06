package com.senlainc.warsaw.tyurin.service;

import com.senlainc.warsaw.tyurin.entity.Role;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByNameException;

public interface IRoleService {

    Role find(long id) throws NotFoundByIdException;

    Role find(String name) throws NotFoundByNameException;
}