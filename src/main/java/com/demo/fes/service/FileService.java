package com.demo.fes.service;

import com.demo.fes.entity.File;
import com.demo.fes.exception.OperationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public interface FileService {
    File uploadFile(MultipartFile file, String size, Long id) throws OperationException, IOException;
    Set<File> getAllFiles(Long id);
    Set<File> getAllUserFiles(Long id);
    Set<File> getAllSharedFiles(Long id);
    Optional<File> getFileById(Long id);
    void deleteFile(Long fileId,Long userId);
    void shareFile(Long id,String email) throws OperationException;
}
