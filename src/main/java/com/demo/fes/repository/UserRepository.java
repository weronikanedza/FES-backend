package com.demo.fes.repository;

import com.demo.fes.entity.User;

public interface UserRepository extends GenericRepository<User,Long>{
//    @Modifying
//    @Query("update User set user.enabled = ?1 where user.id_user = ?2")
//    void setEnabled(Boolean enabled, Long id);

    User findByEmail(String email);
}
