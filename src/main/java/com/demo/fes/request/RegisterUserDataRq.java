package com.demo.fes.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDataRq implements Serializable {
    private static final long serialVersionUID = -3665542098319007959L;

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String city;
    private String country;
    private String password;
    private String confirmedPassword;
}
