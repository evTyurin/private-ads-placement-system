package com.senlainc.warsaw.tyurin.service;

import com.senlainc.warsaw.tyurin.entity.Comment;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;

public interface ICommentService {

    void create(Comment comment) throws NotFoundByIdException;

    Comment find(long id) throws NotFoundByIdException;
}