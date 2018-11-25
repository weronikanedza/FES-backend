package com.demo.fes.entity;

import com.demo.fes.response.FileRs;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String contentType;

    private Long adderId;

    @Lob
    private byte[] data;

    @ManyToMany(fetch = FetchType.LAZY ,mappedBy = "files")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public static FileRs convertTo(File file){
        return FileRs.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .filePath(file.getFilePath())
                .fileType(file.getFileType())
                .date(file.getDate())
                .numberOfUsers(file.getUsers().size())
                .size(file.getSize())
                .data(file.getData())
                .build();
    }
}
