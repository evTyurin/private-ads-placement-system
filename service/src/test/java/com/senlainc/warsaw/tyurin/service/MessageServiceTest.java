package com.senlainc.warsaw.tyurin.service;

import com.senlainc.warsaw.tyurin.IMessageRepository;
import com.senlainc.warsaw.tyurin.entity.Message;
import com.senlainc.warsaw.tyurin.entity.User;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import com.senlainc.warsaw.tyurin.service.impl.MessageService;
import com.senlainc.warsaw.tyurin.service.impl.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;
    @Mock
    private IMessageRepository messageRepository;
    private Message message;
    private User sender;
    private User receiver;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(messageRepository);
    }

    @BeforeEach
    void init() {
        message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);

        sender = new User();
        sender.setId(1);

        receiver = new User();
        receiver.setId(2);
    }

    @Test
    void find_findMessageByCorrectId_returnMessage() throws NotFoundByIdException {
        when(messageRepository.findById(1)).thenReturn(Optional.of(message));

        Message actualMessage = messageRepository.findById(1).orElseThrow(() -> new NotFoundByIdException(1, 404));
        assertEquals(message, actualMessage);
    }

    @Test
    void find_findMessageBySenderIdReceiverId_returnMessage() {
        when(messageRepository.find(sender.getId(), receiver.getId())).thenReturn(Stream.of(message).collect(Collectors.toList()));

        messageService.find(1,2);
    }
}
