package com.demo.fes.service.helper.impl;

import com.demo.fes.definitions.ApiMessages;
import com.demo.fes.service.helper.JSONCreatorService;
import org.springframework.stereotype.Service;

import javax.json.Json;
import java.text.MessageFormat;

@Service
public class JSONCreatorServiceImpl implements JSONCreatorService {

    @Override
    public String constructRegistrationResponse(String token, String email) {

        return createJsonForRegistrationProcess(token, email, ApiMessages.REGISTRATION_TOKEN_SEND);
    }

    @Override
    public String constructResetTokenResponse(String token,  String email) {
        return createJsonForRegistrationProcess(token, email, ApiMessages.RESET_TOKEN_SEND);
    }

    private String createJsonForRegistrationProcess( String token,  String email,  String message) {
        return Json.createObjectBuilder()
                .add("token", token)
                .add("email", email)
                .add("message", MessageFormat.format(message, email))
                .build().toString();
    }
}
