package com.senlainc.warsaw.tyurin.controller;

import com.senlainc.warsaw.tyurin.dto.CommentDto;
import com.senlainc.warsaw.tyurin.mapper.CommentMapper;
import com.senlainc.warsaw.tyurin.service.ICommentService;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * RestController that handles requests to /comments url
 * Contains operations with comments
 */
@Slf4j
@RestController
@RequestMapping("comments")
public class CommentController {

    private ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Add new comment to advertisement
     *
     * @param commentDto the comment dto
     * @throws NotFoundByIdException indicates that comment with this name already exist
     */
    @PostMapping()
    public ResponseEntity<Void> create(@Valid @RequestBody CommentDto commentDto) throws NotFoundByIdException {
        commentService.create(CommentMapper.mapToEntity(commentDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get comment dto by id
     *
     * @param id the comment id
     * @return the comment dto
     * @throws NotFoundByIdException indicates that comment with this id not exist
     */
    @GetMapping("{id}")
    public CommentDto find(@PathVariable long id) throws NotFoundByIdException {
        return CommentMapper.mapToDto(commentService.find(id));
    }
}

