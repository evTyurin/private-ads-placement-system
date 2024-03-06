package com.senlainc.warsaw.tyurin.service.impl;

import com.senlainc.warsaw.tyurin.IMessageRepository;
import com.senlainc.warsaw.tyurin.constants.ExceptionCode;
import com.senlainc.warsaw.tyurin.entity.Message;
import com.senlainc.warsaw.tyurin.entity.User;
import com.senlainc.warsaw.tyurin.service.IMessageService;
import com.senlainc.warsaw.tyurin.service.IUserService;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MessageService implements IMessageService {

    private IMessageRepository messageRepository;
    private IUserService userService;

    public MessageService(IMessageRepository messageRepository,
                          IUserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @Override
    public void create(Message message) throws NotFoundByIdException {
        User receiver = userService.find(message.getReceiver().getId());
        User sender = userService.find(message.getSender().getId());
        message.setReceiver(receiver);
        message.setSender(sender);
        messageRepository.create(message);
    }

    @Override
    public Message find(long messageId) throws NotFoundByIdException {
        return messageRepository.findById(messageId)
                .orElseThrow(()
                        -> new NotFoundByIdException(messageId, ExceptionCode.NOT_FOUND_EXCEPTION_CODE));
    }

    @Override
    public List<Message> find(long senderId, long receiverId) {
        return messageRepository.find(senderId,receiverId);
    }
}