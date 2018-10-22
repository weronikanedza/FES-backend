package com.demo.fes.service.impl;

import com.demo.fes.definitions.ErrorMessages;
import com.demo.fes.entity.User;
import com.demo.fes.entity.UserData;
import com.demo.fes.entity.VerificationToken;
import com.demo.fes.exception.OperationException;
import com.demo.fes.repository.UserDataRepository;
import com.demo.fes.repository.UserRepository;
import com.demo.fes.repository.VerificationTokenRepository;
import com.demo.fes.service.RegistrationService;
import com.demo.fes.service.UserService;
import com.demo.fes.service.helper.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class RegistrationServiceImpl extends AbstractGenericService<UserData, Long> implements RegistrationService {
    private UserRepository userRepository;
    private VerificationTokenRepository tokenRepository;
    private EmailService emailService;
    private UserDataRepository userDataRepository;
    private UserService userService;

    @Autowired
    RegistrationServiceImpl(EmailService emailService, VerificationTokenRepository tokenRepository, UserDataRepository userDataRepository,
                            UserService userService, UserRepository userRepository) {
        this.userDataRepository = userDataRepository;
        this.userService = userService;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
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
    public ResponseEntity sendRegistrationEmail(VerificationToken token, HttpServletRequest request) throws OperationException {
        String vtoken = token.getToken();
        User user = token.getUser();
        emailService.constructRegistrationEmail(getAppUrl(request), vtoken, user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public ResponseEntity confirmRegistration(String token) throws OperationException {
        VerificationToken vtoken = tokenRepository.findByToken(token);

        if (vtoken == null)
            throw new OperationException(HttpStatus.BAD_REQUEST, ErrorMessages.TOKEN_NOT_FOUND);

        User user = vtoken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    public User retrieveByEmail(String email) {
        return userService.retrieveByEmail(email);
    }

    private void checkIfUserExists(UserData user) throws OperationException {
        if (retrieveByEmail(user.getUser().getEmail()) != null) {
            throw new OperationException(HttpStatus.BAD_REQUEST, ErrorMessages.USER_EXISTS);
        }
    }


}
