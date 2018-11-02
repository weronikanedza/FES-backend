package com.demo.fes.service;

import com.demo.fes.entity.File;
import com.demo.fes.exception.OperationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    File uploadFile(MultipartFile file, String size, Long id) throws OperationException, IOException;
}
