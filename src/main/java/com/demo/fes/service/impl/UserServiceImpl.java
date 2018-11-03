package com.demo.fes.service.impl;

import com.demo.fes.definitions.ErrorMessages;
import com.demo.fes.entity.User;
import com.demo.fes.entity.UserData;
import com.demo.fes.exception.OperationException;
import com.demo.fes.repository.UserDataRepository;
import com.demo.fes.repository.UserRepository;
import com.demo.fes.request.LoginRq;
import com.demo.fes.response.UserRs;
import com.demo.fes.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractGenericService<User, Long> implements UserService {
    private UserRepository userRepository;
    private UserDataRepository userDataRepository;


    public UserServiceImpl(UserRepository userRepository, UserDataRepository userDataRepository) {
        this.userRepository = userRepository;
        this.userDataRepository = userDataRepository;
    }

    @Override
    public User retrieveByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserRs loginUser(LoginRq loginRq) throws OperationException {
        User user = retrieveByEmail(loginRq.getEmail());

        if (user == null || !user.getPassword().equals(loginRq.getPassword()) || !user.getEnabled()) {
            throw new OperationException(HttpStatus.BAD_REQUEST, ErrorMessages.USER_NOT_EXISTS);
        }

        UserData userdata = userDataRepository.findByUser(user);

        return UserRs.builder().role(user.getRole())
                .firstName(userdata.getFirstName())
                .lastName(userdata.getLastName())
                .id(user.getId())
                .build();

    }


}
