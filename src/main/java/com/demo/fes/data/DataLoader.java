package com.demo.fes.data;

import com.demo.fes.entity.User;
import com.demo.fes.entity.UserData;
import com.demo.fes.exception.OperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.demo.fes.service.RegistrationService;

@Component
public class DataLoader implements ApplicationRunner {
    private static String DEFAULT_ROLE = "USER";
    private static String ADMIN_ROLE = "ADMIN";

    private RegistrationService registartionService ;

    @Autowired
    public DataLoader(RegistrationService registartionService){
        this.registartionService = registartionService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
     //   createExampleData();
    }

    private void createExampleData() throws OperationException {
        UserData user =
                UserData.builder().firstName("Jan")
                        .lastName("Kowalski")
                        .dateOfBirth("12-09-1992")
                        .city("Krakow")
                        .country("Poland")
                        .user(createUsersLoginData("a@b.com", "user", DEFAULT_ROLE))
                        .build();

        registartionService.registerUser(user);

        UserData admin =
                UserData.builder().firstName("Jarek")
                        .lastName("Muzykant")
                        .dateOfBirth("12-09-1990")
                        .city("Warsaw")
                        .country("Poland")
                        .user(createUsersLoginData("jarek@b.com", "admin", ADMIN_ROLE))
                        .build();

        registartionService.registerUser(admin);
    }

    private User createUsersLoginData(String email, String password, String role) {
        return User.builder().email(email).password(password).role(role).enabled(false).build();
    }

    private void createFiles() {
    }
}
