package com.demo.fes.controller;


import com.demo.fes.entity.User;
import com.demo.fes.entity.UserData;
import com.demo.fes.entity.VerificationToken;
import com.demo.fes.exception.OperationException;
import com.demo.fes.request.RegisterUserDataRq;
import com.demo.fes.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String registerUser(@RequestBody RegisterUserDataRq userDataRq, HttpServletRequest request) throws OperationException {

        UserData userData = registrationService.registerUser(UserData.convertFromJson(userDataRq));
        User registered = userData.getUser();

        VerificationToken vToken = registrationService.createVerificationToken(registered);
        String json = registrationService.sendRegistrationEmail(vToken, request);

        //   return ResponseEntity.status(HttpStatus.CREATED).body(json);
        return "regi";
    }

    @RequestMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
