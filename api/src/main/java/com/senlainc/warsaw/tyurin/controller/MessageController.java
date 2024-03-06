package com.senlainc.warsaw.tyurin.controller;

import com.senlainc.warsaw.tyurin.dto.MessageDto;
import com.senlainc.warsaw.tyurin.dto.MessageSearchDto;
import com.senlainc.warsaw.tyurin.mapper.MessageMapper;
import com.senlainc.warsaw.tyurin.service.IMessageService;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RestController that handles requests to /messages url
 * Contains operations with messages
 */
@Slf4j
@RestController
@RequestMapping("messages")
public class MessageController {

    private IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Add new message between seller and customer
     *
     * @param messageDto             the message dto
     * @throws NotFoundByIdException indicates that seller or customer is not exist
     */
    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody MessageDto messageDto) throws NotFoundByIdException {
        messageService.create(MessageMapper.mapToEntity(messageDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get dialog between customer and seller
     *
     * @param messageSearchDto dto that include id of customer and id of seller
     * @return                 list of message dto between customer and seller sorted by creation date desc
     * @throws NotFoundByIdException indicates that seller or customer is not exist
     */
    @GetMapping()
    public List<MessageDto> find(@RequestBody MessageSearchDto messageSearchDto) throws NotFoundByIdException {
        return messageService
                .find(messageSearchDto.getSenderId(), messageSearchDto.getReceiverId())
                .stream()
                .map(MessageMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
