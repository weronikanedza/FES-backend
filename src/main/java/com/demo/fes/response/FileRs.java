package com.demo.fes.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileRs implements Serializable {
    private Long id;
    private String fileName;
    private String filePath;
    private String fileType;
    private String date;
    private String size;
    private Integer numberOfUsers;
    private byte[] data;
}


