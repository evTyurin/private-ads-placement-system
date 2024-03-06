package com.senlainc.warsaw.tyurin.service.impl;

import com.senlainc.warsaw.tyurin.ICommentRepository;
import com.senlainc.warsaw.tyurin.constants.ExceptionCode;
import com.senlainc.warsaw.tyurin.entity.Advertisement;
import com.senlainc.warsaw.tyurin.entity.Comment;
import com.senlainc.warsaw.tyurin.entity.User;
import com.senlainc.warsaw.tyurin.service.IAdvertisementService;
import com.senlainc.warsaw.tyurin.service.ICommentService;
import com.senlainc.warsaw.tyurin.service.IUserService;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommentService implements ICommentService {

    private ICommentRepository commentRepository;
    private IUserService userService;
    private IAdvertisementService advertisementService;

    public CommentService(ICommentRepository commentRepository,
                          IUserService userService,
                          IAdvertisementService advertisementService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.advertisementService = advertisementService;
    }

    @Override
    public void create(Comment comment) throws NotFoundByIdException {
        Advertisement advertisement = advertisementService
                .find(comment
                        .getAdvertisement()
                        .getId());
        User sender = userService.find(comment
                .getUser()
                .getId());
        comment.setAdvertisement(advertisement);
        comment.setUser(sender);
        commentRepository.create(comment);
    }

    @Override
    public Comment find(long id) throws NotFoundByIdException {
        return commentRepository.findById(id)
                .orElseThrow(()
                        -> new NotFoundByIdException(id, ExceptionCode.NOT_FOUND_EXCEPTION_CODE));
    }
}
