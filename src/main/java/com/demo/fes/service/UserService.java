package com.demo.fes.service;

import com.demo.fes.entity.User;
import com.demo.fes.exception.OperationException;
import com.demo.fes.request.LoginRq;

public interface UserService {
    User retrieveByEmail(String email);
    String loginUser(LoginRq loginRq) throws OperationException;
}
