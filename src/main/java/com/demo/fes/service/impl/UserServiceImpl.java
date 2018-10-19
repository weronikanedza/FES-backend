package com.demo.fes.service.impl;

import com.demo.fes.entity.User;
import com.demo.fes.repository.UserRepository;
import com.demo.fes.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractGenericService<User,Long> implements UserService {
   private UserRepository userRepository;


   public UserServiceImpl(UserRepository userRepository){
     //  super(userRepository);

   }
    @Override
    public User retrieveByEmail(String email) {
        return null;
    }

}
