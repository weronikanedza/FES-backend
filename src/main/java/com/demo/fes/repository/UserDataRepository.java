package com.demo.fes.repository;

import com.demo.fes.entity.User;
import com.demo.fes.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
    UserData findByUser (User user);
}
