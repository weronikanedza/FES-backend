package com.demo.fes.controller;


import com.demo.fes.entity.User;
import com.demo.fes.entity.UserData;
import com.demo.fes.entity.VerificationToken;
import com.demo.fes.exception.OperationException;
import com.demo.fes.request.RegisterUserDataRq;
import com.demo.fes.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.demo.fes.request.TokenRq;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
public class RegistrationController {
    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerUser(@RequestBody RegisterUserDataRq userDataRq, HttpServletRequest request) throws OperationException {

        UserData userData = registrationService.registerUser(UserData.convertFromJson(userDataRq));
        User registered = userData.getUser();

        VerificationToken vToken = registrationService.createVerificationToken(registered);
        return registrationService.sendRegistrationEmail(vToken, request);
    }

    @PostMapping(path = "/register/confirm")
    public ResponseEntity confirmRegistration
            (@RequestBody TokenRq token) throws OperationException {
        return registrationService.confirmRegistration(token.getToken());
    }
}
