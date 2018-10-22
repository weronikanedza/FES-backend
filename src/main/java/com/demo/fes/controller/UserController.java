package com.demo.fes.controller;

import com.demo.fes.exception.OperationException;
import com.demo.fes.request.LoginRq;
import com.demo.fes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/login")
    public String login(@RequestBody LoginRq loginRq) throws OperationException {
       return userService.loginUser(loginRq);
    }
}
