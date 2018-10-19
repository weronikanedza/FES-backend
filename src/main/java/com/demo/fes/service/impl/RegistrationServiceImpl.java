package com.demo.fes.service.impl;

import com.demo.fes.definitions.ErrorMessages;
import com.demo.fes.entity.User;
import com.demo.fes.entity.UserData;
import com.demo.fes.entity.VerificationToken;
import com.demo.fes.exception.OperationException;
import com.demo.fes.repository.TokenRepository;
import com.demo.fes.repository.UserDataRepository;
import com.demo.fes.repository.UserRepository;
import com.demo.fes.service.RegistrationService;
import com.demo.fes.service.helper.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class RegistrationServiceImpl extends AbstractGenericService<UserData,Long> implements RegistrationService {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private EmailService emailService;
    private UserDataRepository userDataRepository;

    @Autowired
    RegistrationServiceImpl(EmailService emailService, TokenRepository tokenRepository, UserDataRepository userDataRepository, UserRepository userRepository) {
        this.userDataRepository = userDataRepository;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @Override
    public UserData registerUser(UserData userData) throws OperationException {
       // checkIfUserExists(userData);
        return userDataRepository.save(userData);
    }

    @Override
    public VerificationToken createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken myToken = new VerificationToken(token, user);
        return tokenRepository.save(myToken);
    }

    @Override
    public String sendRegistrationEmail(VerificationToken token, HttpServletRequest request) throws OperationException {
        String vtoken = token.getToken();
        User user = token.getUser();
        emailService.constructRegistrationEmail(getAppUrl(request), vtoken, user);

        return jsonService.constructRegistrationResponse(vtoken, user.getEmail());
    }

    @Override
    public User retrieveByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private void checkIfUserExists(UserData user) throws OperationException {
        if (retrieveByEmail(user.getUser().getEmail()) != null) {
            throw new OperationException(HttpStatus.BAD_REQUEST, ErrorMessages.USER_EXISTS);
        }
    }


}
