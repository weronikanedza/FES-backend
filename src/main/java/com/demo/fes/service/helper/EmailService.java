package com.demo.fes.service.helper;

import com.demo.fes.entity.User;
import com.demo.fes.exception.OperationException;

public interface EmailService {
        void sendSimpleMessage(final String to, final String subject, final String text) throws OperationException;

//        void sendSimpleMessage(EmailMessage userMessage);
//
//        void sendMessageWithAttachment(final String to, final String subject, final String text, final String pathToAttachment) throws MessagingException;
//
          void constructRegistrationEmail(final String contextPath, final String token, final User user) throws OperationException;
//
//        void constructForgotPasswordTokenEmail(final String contextPath, final String token, final User user) throws OperationException;
//
//        void constructReviewerRegistrationEmail(final String to, String password) throws OperationException;
//
//        void constructAddReviewerAuthority(final String to) throws OperationException;
}
