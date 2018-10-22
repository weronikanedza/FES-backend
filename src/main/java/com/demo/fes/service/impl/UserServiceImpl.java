package com.demo.fes.service.impl;

import com.demo.fes.definitions.ErrorMessages;
import com.demo.fes.entity.User;
import com.demo.fes.exception.OperationException;
import com.demo.fes.repository.UserRepository;
import com.demo.fes.request.LoginRq;
import com.demo.fes.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractGenericService<User,Long> implements UserService {
   private UserRepository userRepository;


   public UserServiceImpl(UserRepository userRepository){
      this.userRepository=userRepository;

   }
    @Override
    public User retrieveByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String loginUser(LoginRq loginRq) throws OperationException {
        User user = retrieveByEmail(loginRq.getEmail());

        if(user == null || !user.getPassword().equals(loginRq.getPassword()) || !user.getEnabled()){
            throw new OperationException(HttpStatus.BAD_REQUEST, ErrorMessages.USER_NOT_EXISTS);
        }

        return user.getRole();

    }



}
