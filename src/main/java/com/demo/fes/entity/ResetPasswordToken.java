package com.demo.fes.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "resetPasswordToken")
public class ResetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date expiryDate;
}
