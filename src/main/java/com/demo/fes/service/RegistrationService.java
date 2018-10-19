package com.demo.fes.service;

import com.demo.fes.entity.User;
import com.demo.fes.entity.UserData;
import com.demo.fes.entity.VerificationToken;
import com.demo.fes.exception.OperationException;

import javax.servlet.http.HttpServletRequest;

public interface RegistrationService extends UserService {
    UserData registerUser(UserData userData) throws OperationException;
    VerificationToken createVerificationToken(User user);
    String sendRegistrationEmail(VerificationToken token, HttpServletRequest request) throws OperationException;
}
