package com.senlainc.warsaw.tyurin.service.impl;

import com.senlainc.warsaw.tyurin.entity.User;
import com.senlainc.warsaw.tyurin.service.IMailSenderService;
import com.senlainc.warsaw.tyurin.service.IUserService;
import com.senlainc.warsaw.tyurin.service.exception.EntityExistException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByNameException;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;

@Slf4j
@Service
public class MailSenderService implements IMailSenderService {

    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    private IUserService userService;

    public MailSenderService(JavaMailSender mailSender,
                             VelocityEngine velocityEngine,
                             IUserService userService) {
        this.mailSender = mailSender;
        this.velocityEngine = velocityEngine;
        this.userService = userService;
    }

    private static final Logger logger = LoggerFactory.getLogger(MailSenderService.class);

    @Transactional
    @Override
    public void sendResetPasswordEmail(String email) throws NotFoundByNameException, NotFoundByIdException, EntityExistException, MessagingException, EntityExistException {
        User user = userService.find(email);
        String templateName = "templates/reset_password_email.vm";
        String emailHeader = "Password reset request";
        String resetPasswordToken = generateResetPasswordToken();
        String emailInput = "http://localhost:8080/mails/get-temporary-password?id="+user.getId()+"&token="+resetPasswordToken;
        sendEmail(email, templateName, emailHeader, emailInput);
        logger.info("reset password link send successfully");
        user.setResetPasswordToken(resetPasswordToken);
        userService.update(user.getId(), user);
    }

    @Transactional
    @Override
    public void sendTemporaryPasswordEmail(long userId, String resetPasswordToken) throws NotFoundByIdException, MessagingException {
        User user = userService.find(userId);
        String templateName = "templates/temporary_password_email.vm";
        String emailHeader = "Updated password";
        String emailInput = userService.createTemporaryPassword(userId, resetPasswordToken);
        sendEmail(user.getEmail(), templateName, emailHeader, emailInput);
        logger.info("temporary password send successfully");
    }

    private void sendEmail(String email, String templateName, String emailHeader, String emailInput) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Template template = velocityEngine.getTemplate(templateName);
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("input", emailInput);
        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);
        helper.setTo(email);
        helper.setSubject(emailHeader);
        helper.setText(stringWriter.toString(), true);
        mailSender.send(message);
    }

    private String generateResetPasswordToken() {
        return RandomString.make(30);
    }
}