package com.demo.fes.service.impl;

import com.demo.fes.entity.UserData;
import com.demo.fes.repository.UserDataRepository;
import com.demo.fes.service.IUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataServiceImpl extends AbstractGenericService<UserData,Long> implements IUserDataService {
    UserDataRepository userDataRepository;

    @Autowired
    public UserDataServiceImpl(UserDataRepository userDataRepository) {
        super(userDataRepository);
    }

}
