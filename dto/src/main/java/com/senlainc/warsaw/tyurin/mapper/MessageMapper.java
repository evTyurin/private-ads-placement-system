package com.senlainc.warsaw.tyurin.mapper;

import com.senlainc.warsaw.tyurin.dto.MessageDto;
import com.senlainc.warsaw.tyurin.entity.Message;
import com.senlainc.warsaw.tyurin.entity.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class MessageMapper {

    public Message mapToEntity(MessageDto dto) {
        return Message
                .builder()
                .sender(User
                        .builder()
                        .id(dto.getSenderId())
                        .build())
                .receiver(User
                        .builder()
                        .id(dto.getReceiverId())
                        .build())
                .message(dto.getMessage())
                .creationDateTime(LocalDateTime.now())
                .build();
    }

    public MessageDto mapToDto(Message message) {
        return MessageDto
                .builder()
                .id(message.getId())
                .senderId(message.getSender().getId())
                .receiverId(message.getReceiver().getId())
                .message(message.getMessage())
                .creationDateTime(message.getCreationDateTime())
                .build();
    }
}
