package com.senlainc.warsaw.tyurin.service;

import com.senlainc.warsaw.tyurin.ICommentRepository;
import com.senlainc.warsaw.tyurin.ISellerEvaluationRepository;
import com.senlainc.warsaw.tyurin.entity.Advertisement;
import com.senlainc.warsaw.tyurin.entity.Comment;
import com.senlainc.warsaw.tyurin.entity.User;
import com.senlainc.warsaw.tyurin.service.exception.EntityExistException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByNameException;
import com.senlainc.warsaw.tyurin.service.impl.CommentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private IUserService userService;
    @Mock
    private IAdvertisementService advertisementService;
    @Mock
    private ICommentRepository commentRepository;
    @Mock
    private ISellerEvaluationRepository sellerEvaluationRepository;
    private User sender;
    private User receiver;
    private Advertisement advertisement;
    private Comment comment;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(userService, sellerEvaluationRepository, advertisementService,commentRepository);
    }

    @BeforeEach
    void init() {
        sender = new User();
        sender.setId(1);

        receiver = new User();
        receiver.setId(2);

        advertisement = new Advertisement();
        advertisement.setId(1);
        advertisement.setSeller(sender);
        advertisement.setCustomer(receiver);

        comment = new Comment();
        comment.setId(1);
        comment.setUser(receiver);
        comment.setAdvertisement(advertisement);
    }

    @Test
    void find_findCommentByCorrectId_returnComment() throws NotFoundByIdException {
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));

        Comment actualComment = commentRepository.findById(1).orElseThrow(() -> new NotFoundByIdException(1, 404));
        assertEquals(comment, actualComment);
    }

    @Test
    void create_createSellerEvaluation_createSuccessfully() throws NotFoundByIdException, EntityExistException, NotFoundByNameException {
        when(advertisementService.find(1)).thenReturn(advertisement);
        when(userService.find(2)).thenReturn(receiver);

        commentService.create(comment);

        verify(commentRepository).create(comment);
    }
}
