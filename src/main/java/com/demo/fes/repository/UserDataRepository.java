package com.demo.fes.repository;

import com.demo.fes.entity.User;
import com.demo.fes.entity.UserData;

public interface UserDataRepository extends GenericRepository<UserData,Long> {
    UserData findByUser (User user);
}
