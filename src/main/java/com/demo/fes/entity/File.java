package com.demo.fes.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fileName;

    @NotBlank
    private String filePath;

    @NotBlank
    private String fileType;

}
