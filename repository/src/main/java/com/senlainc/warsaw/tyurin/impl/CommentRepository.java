package com.senlainc.warsaw.tyurin.impl;

import com.senlainc.warsaw.tyurin.ICommentRepository;
import com.senlainc.warsaw.tyurin.entity.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository extends AbstractRepository<Comment> implements ICommentRepository {
}
