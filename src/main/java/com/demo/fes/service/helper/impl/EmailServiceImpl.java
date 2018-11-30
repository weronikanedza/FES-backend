package com.demo.fes.service.helper.impl;

import com.demo.fes.entity.User;
import com.demo.fes.exception.OperationException;
import com.demo.fes.helper.EmailHelper;
import com.demo.fes.service.helper.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    private static String EMAIL = "fes.noreply@gmail.com";
    private EmailHelper emailHelper;
    private JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(EmailHelper emailHelper) {
        this.emailHelper = emailHelper;
        this.emailSender=emailHelper.mailSender();
    }


    @Override
    public void sendSimpleMessage(String to, String subject, String text) throws OperationException {

        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessage.setContent(text, "text/html;charset=UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(EMAIL);
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new OperationException(HttpStatus.REQUEST_TIMEOUT, "Email has not been sent");
        }
    }

    @Override
    public void constructRegistrationEmail(String contextPath, String token, User user) throws OperationException {
        String recipientAddress = user.getEmail();
        String subject = "Registration confirmation";
        final String confirmationUrl
                = contextPath + ":3000/registration/confirm?token=" + token;
        final String message = "\nTo enable your account click in the link:\n" +
                "<a href=" + confirmationUrl + ">Account confirmation</a>";

        sendSimpleMessage(recipientAddress, subject, message);
    }
}
