package com.senlainc.warsaw.tyurin;

import com.senlainc.warsaw.tyurin.entity.Comment;

import java.util.Optional;

public interface ICommentRepository {

    Optional<Comment> findById(long id);

    void create(Comment comment);
}