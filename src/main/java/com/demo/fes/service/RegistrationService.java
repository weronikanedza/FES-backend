package com.demo.fes.service;

import com.demo.fes.entity.User;
import com.demo.fes.entity.UserData;
import com.demo.fes.entity.VerificationToken;
import com.demo.fes.exception.OperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface RegistrationService {
    UserData registerUser(UserData userData) throws OperationException;
    VerificationToken createVerificationToken(User user);
    ResponseEntity sendRegistrationEmail(VerificationToken token, HttpServletRequest request) throws OperationException;
    ResponseEntity<HttpStatus> confirmRegistration(String token) throws OperationException;
}
