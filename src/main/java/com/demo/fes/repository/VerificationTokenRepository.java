package com.demo.fes.repository;

import com.demo.fes.entity.VerificationToken;

public interface VerificationTokenRepository extends GenericRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);
}
