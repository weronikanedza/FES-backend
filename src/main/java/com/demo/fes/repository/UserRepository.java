package com.demo.fes.repository;

import com.demo.fes.entity.User;

public interface UserRepository extends GenericRepository<User,Long>{
    User findByEmail(String email);
}
