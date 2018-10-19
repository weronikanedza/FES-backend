package com.demo.fes.entity;

import com.demo.fes.request.RegisterUserDataRq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private static final String DEFAULT_ROLE = "USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_data")
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String dateOfBirth;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private User user;

    private static User createUser(RegisterUserDataRq registerUserRq){
        return User.builder()
                .email(registerUserRq.getEmail())
                .password(registerUserRq.getPassword())
                .role(DEFAULT_ROLE)
                .enabled(false)
                .build();
    }
    public static UserData convertFromJson(RegisterUserDataRq registerUserRq) {
        if (registerUserRq == null)
            return null;

        return UserData.builder().id(registerUserRq.getId())
                .firstName(registerUserRq.getFirstName())
                .lastName(registerUserRq.getLastName())
                .dateOfBirth(registerUserRq.getDateOfBirth())
                .city(registerUserRq.getCity())
                .country(registerUserRq.getCountry())
                .user(createUser(registerUserRq))
                .build();
    }
}
