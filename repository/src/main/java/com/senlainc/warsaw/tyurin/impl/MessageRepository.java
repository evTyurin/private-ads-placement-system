package com.senlainc.warsaw.tyurin.impl;

import com.senlainc.warsaw.tyurin.IMessageRepository;
import com.senlainc.warsaw.tyurin.entity.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class MessageRepository extends AbstractRepository<Message> implements IMessageRepository {

    @Override
    public List<Message> find(long senderId, long receiverId) {
        final String FIND_ADVERTISEMENT_BY_SELLER_ID = "SELECT message FROM Message message WHERE message.sender.id=:senderId and message.receiver.id=:receiverId or message.receiver.id=:senderId and message.sender.id=:receiverId order by message.creationDateTime desc";
        TypedQuery<Message> query = entityManager.createQuery(FIND_ADVERTISEMENT_BY_SELLER_ID, Message.class);
        query.setParameter("senderId", senderId);
        query.setParameter("receiverId", receiverId);
        return query.getResultList();
    }
}
