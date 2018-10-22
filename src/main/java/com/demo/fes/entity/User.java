package com.demo.fes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

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

    @Column(name = "enabled", columnDefinition = "BOOLEAN")
    private Boolean enabled;

    private String role;

    @OneToOne(mappedBy = "user",cascade=CascadeType.ALL)
    private UserData dataAboutUser;

    public static EntityManager entityManager;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
