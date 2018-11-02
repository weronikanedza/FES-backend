package com.demo.fes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_file")
    private Long id;

    @NotBlank
    private String fileName;

    @NotBlank
    private String filePath;

    @NotBlank
    private String fileType;

    private String date;

    private String size;


    @ManyToMany(mappedBy = "files")
    private Set<User> users = new HashSet<>();
}
