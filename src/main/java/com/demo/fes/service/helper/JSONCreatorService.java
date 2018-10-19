package com.demo.fes.service.helper;

/**
 * Service that helps creating custom JSON
 */
public interface JSONCreatorService {
    String constructRegistrationResponse(final String token, final String email);

    String constructResetTokenResponse(String token, String email);
}
