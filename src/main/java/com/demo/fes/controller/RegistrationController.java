package com.demo.fes.controller;


import com.demo.fes.request.RegisterUserDataRq;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping
public class RegistrationController {
//    private RegistrationService registrationService;
//    @Autowired
//    public RegistrationController(RegistrationService registrationService){
//        this.registrationService=registrationService;
//    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String registerUser(@RequestBody @Valid RegisterUserDataRq userDataRq, HttpServletRequest request) {

        System.out.println("JESTEM");
      // User registered = registrationService.save(convertToEntity(user));

        // if registered successfully then create verification token and send an email
     //   final VerificationToken vToken = registrationService.createVerificationToken(registered, request);
      //  final String json = registrationService.sendRegistrationEmail(vToken, request);

     //   return ResponseEntity.status(HttpStatus.CREATED).body(json);
        return "regi";
    }

    @RequestMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
