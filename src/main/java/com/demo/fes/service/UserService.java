package com.demo.fes.service;

import com.demo.fes.entity.User;
import com.demo.fes.exception.OperationException;
import com.demo.fes.request.ChangePasswordRq;
import com.demo.fes.request.EditUserDataDto;
import com.demo.fes.request.LoginRq;
import com.demo.fes.response.UserRs;

public interface UserService {
    User retrieveByEmail(String email);
    UserRs loginUser(LoginRq loginRq) throws OperationException;
    void editUser(EditUserDataDto editUserDataDto);
    EditUserDataDto getUser(Long id);
    void changePassword(ChangePasswordRq changePasswordRq) throws OperationException;
    void resetPassword (String email) throws OperationException;
}
