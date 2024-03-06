package com.senlainc.warsaw.tyurin.controller;

import com.senlainc.warsaw.tyurin.dto.MailRequestDto;
import com.senlainc.warsaw.tyurin.service.IMailSenderService;
import com.senlainc.warsaw.tyurin.service.exception.EntityExistException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByNameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;

/**
 * RestController that handles requests to /mails url
 * Contains operations with mail sending
 */
@Slf4j
@RestController
@RequestMapping("mails")
public class MailController {

    private IMailSenderService mailSenderService;

    public MailController(IMailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    /**
     * Send reset password mail that include link for sending temporary password
     *
     * @param mailRequestDto the mail request dto
     * @return
     * @throws NotFoundByNameException indicates that user with this email not exist
     * @throws NotFoundByIdException   indicates that user with this id not exist
     * @throws EntityExistException    indicates that user with this email already exist
     * @throws MessagingException      indicates that the connect method fails due to an authentication failure
     */
    @PostMapping("reset-password")
    public ResponseEntity<Void> sendLostPasswordEmail(@RequestBody @Valid MailRequestDto mailRequestDto) throws NotFoundByNameException,
            NotFoundByIdException,
            EntityExistException,
            MessagingException {
        mailSenderService.sendResetPasswordEmail(mailRequestDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Send temporary password mail that include new password
     *
     * @param userId             the user id
     * @param resetPasswordToken the reset password token
     * @return
     * @throws NotFoundByNameException indicates that user with this email not exist
     * @throws NotFoundByIdException   indicates that user with this id not exist
     * @throws EntityExistException    indicates that user with this email already exist
     * @throws MessagingException      indicates that the connect method fails due to an authentication failure
     */
    @GetMapping("get-temporary-password")
    public ResponseEntity<Void> sendTemporaryPasswordEmail(@RequestParam(name = "id") long userId,
                                                           @RequestParam(name = "token") String resetPasswordToken) throws NotFoundByNameException,
            NotFoundByIdException,
            EntityExistException,
            MessagingException {
        mailSenderService.sendTemporaryPasswordEmail(userId, resetPasswordToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
