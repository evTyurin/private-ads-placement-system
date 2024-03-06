package com.senlainc.warsaw.tyurin.service;

import com.senlainc.warsaw.tyurin.entity.Message;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;

import java.util.List;

public interface IMessageService {

    void create(Message message) throws NotFoundByIdException;

    Message find(long messageId) throws NotFoundByIdException;

    List<Message> find(long senderId, long receiverId);
}