package com.demo.fes.service;

import com.demo.fes.entity.User;
import com.demo.fes.exception.OperationException;
import com.demo.fes.request.LoginRq;
import com.demo.fes.response.UserRs;

public interface UserService {
    User retrieveByEmail(String email);
    UserRs loginUser(LoginRq loginRq) throws OperationException;
}
