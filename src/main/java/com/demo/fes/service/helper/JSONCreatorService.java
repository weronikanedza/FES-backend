package com.demo.fes.service.helper;

/**
 * Service that helps creating custom JSON
 */
public interface JSONCreatorService {
    String constructRegistrationResponse( String token,  String email);

    String constructResetTokenResponse(String token, String email);
}
