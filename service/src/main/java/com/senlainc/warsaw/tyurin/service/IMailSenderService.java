package com.senlainc.warsaw.tyurin.service;

import com.senlainc.warsaw.tyurin.service.exception.EntityExistException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByNameException;

import javax.mail.MessagingException;

public interface IMailSenderService {

    void sendResetPasswordEmail(String email) throws NotFoundByNameException, NotFoundByIdException, EntityExistException, MessagingException;

    void sendTemporaryPasswordEmail(long userId, String resetPasswordToken) throws NotFoundByNameException, NotFoundByIdException, EntityExistException, MessagingException;
}