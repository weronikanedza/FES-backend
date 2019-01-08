package com.demo.fes.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class VerificationToken {
    private static final int EXPIRATION_TIME = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;


    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id_user")
    private User user;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
    }

}
