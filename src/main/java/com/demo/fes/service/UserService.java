package com.demo.fes.service;

import com.demo.fes.entity.User;

public interface UserService {
    User retrieveByEmail(String email);
}
