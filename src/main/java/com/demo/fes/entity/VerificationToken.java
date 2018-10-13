package com.demo.fes.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "verificationToken")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date expiryDate;

}
