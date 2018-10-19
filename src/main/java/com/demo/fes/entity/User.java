package com.demo.fes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name="User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    public User(){
        this.enabled=false;
    }
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private Boolean enabled;

    private String role;

    @OneToOne(mappedBy = "user",cascade=CascadeType.ALL)
    private UserData dataAboutUser;

}
