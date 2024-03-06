package com.senlainc.warsaw.tyurin;

import com.senlainc.warsaw.tyurin.entity.Message;

import java.util.List;
import java.util.Optional;

public interface IMessageRepository {

    Optional<Message> findById(long id);

    void create(Message message);

    List<Message> find(long senderId, long receiverId);
}