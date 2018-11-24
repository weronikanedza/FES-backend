package com.demo.fes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name="user")
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

    public static EntityManager entityManager;


    @ManyToMany(fetch = FetchType.LAZY )
    @JsonIgnore
    @JoinTable(name = "user_file", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "file_id",
                    nullable = false, updatable = false) })
    private Set<File> files = new HashSet<>();

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
