package com.demo.fes.controller;

import com.demo.fes.exception.OperationException;
import com.demo.fes.request.ChangePasswordRq;
import com.demo.fes.request.EditUserDataDto;
import com.demo.fes.request.LoginRq;
import com.demo.fes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity login(@RequestBody LoginRq loginRq) throws OperationException {
       return  ResponseEntity.ok(userService.loginUser(loginRq));
    }

    @RequestMapping(path = "/getUser")
    public ResponseEntity getUser(@RequestBody String id)  {
        return  ResponseEntity.ok(userService.getUser(Long.valueOf(id.replace("=",""))));
    }

    @RequestMapping(path = "/editUser")
    public ResponseEntity editUser(@RequestBody EditUserDataDto user)  {
        userService.editUser(user);
        return  ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(path = "/changePassword")
    public ResponseEntity changePassword(@RequestBody ChangePasswordRq changePasswordRq) throws OperationException {
        userService.changePassword(changePasswordRq);
        return  ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(path = "/resetPassword")
    public ResponseEntity resetPassword(@RequestBody String email) throws OperationException {
        userService.resetPassword(email.replace("%40","@").replace("=",""));
        return  ResponseEntity.ok(HttpStatus.OK);
    }
}
