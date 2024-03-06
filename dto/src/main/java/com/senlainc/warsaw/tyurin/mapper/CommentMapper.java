package com.senlainc.warsaw.tyurin.mapper;

import com.senlainc.warsaw.tyurin.dto.CommentDto;
import com.senlainc.warsaw.tyurin.entity.Advertisement;
import com.senlainc.warsaw.tyurin.entity.Comment;
import com.senlainc.warsaw.tyurin.entity.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class CommentMapper {

    public Comment mapToEntity(CommentDto dto) {
        return Comment
                .builder()
                .user(User
                        .builder()
                        .id(dto.getUserId())
                        .build())
                .advertisement(Advertisement
                        .builder()
                        .id(dto.getAdvertisementId())
                        .build())
                .comment(dto.getComment())
                .creationDateTime(LocalDateTime.now())
                .build();
    }

    public CommentDto mapToDto(Comment comment) {
        return CommentDto
                .builder()
                .id(comment.getId())
                .userId(comment.getUser().getId())
                .advertisementId(comment.getAdvertisement().getId())
                .comment(comment.getComment())
                .creationDateTime(comment.getCreationDateTime())
                .build();
    }
}
