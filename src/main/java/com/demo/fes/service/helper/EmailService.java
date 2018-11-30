package com.demo.fes.service.helper;

import com.demo.fes.entity.User;
import com.demo.fes.exception.OperationException;

public interface EmailService {
    void sendSimpleMessage(final String to, final String subject, final String text) throws OperationException;
    void constructRegistrationEmail(final String contextPath, final String token, final User user) throws OperationException;
}
