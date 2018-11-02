package com.demo.fes.service.impl;

import com.demo.fes.definitions.ErrorMessages;
import com.demo.fes.entity.User;
import com.demo.fes.exception.OperationException;
import com.demo.fes.repository.FileRepository;
import com.demo.fes.repository.UserRepository;
import com.demo.fes.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class FileServiceImpl implements FileService {
    private UserRepository userRepository;
    private FileRepository fileRepository;
    private static final String SEPARATOR = File.separator;
    private static final String UPLOADED_FOLDER = "files" + SEPARATOR;
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d-MM-uuuu");

    @Autowired
    public FileServiceImpl(UserRepository userRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public com.demo.fes.entity.File uploadFile(MultipartFile file, String size, Long id) throws OperationException, IOException {
        String fileName = file.getOriginalFilename();
        String fileType = getFileType(file);

        checkFile(file,id);
        saveFileInFolder(file, id);

        com.demo.fes.entity.File newFile = createFile(fileName,fileType,size,id);
        Optional<User> user = userRepository.findById(id);
        newFile.getUsers().add(user.get());
        user.get().getFiles().add(newFile);
        return fileRepository.save(newFile);
    }

    private String getFileType(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf(".") + 1).trim();
    }

    private void checkFile(MultipartFile file,Long id) throws OperationException, IOException {
        String fileName = file.getOriginalFilename();
        if (file.isEmpty())
            throw new OperationException(HttpStatus.NOT_ACCEPTABLE, ErrorMessages.EMPTY_FILE);

        Optional<User> user = userRepository.findById(id);
        Set<com.demo.fes.entity.File> files = user.get().getFiles();

        if(isFileWithGivenName(files,fileName,id)){
            throw new OperationException(HttpStatus.NOT_ACCEPTABLE, ErrorMessages.FILE_EXISTS);
        }
    }

    private boolean isFileWithGivenName(Set<com.demo.fes.entity.File> files, String fileName,Long id){
        return files.stream().anyMatch(f ->f.getFileName().equals(id+"_"+fileName));
    }
    private void saveFileInFolder(MultipartFile file, Long id) throws IOException {
        String fileName = file.getOriginalFilename();
        Files.write(Paths.get(UPLOADED_FOLDER + id + "_" + fileName), file.getBytes());
    }

    private com.demo.fes.entity.File createFile(String fileName, String fileType, String size, Long id){
        Set<User> userSet = new HashSet<>();
        userSet.add(userRepository.findById(id).get());

        return  com.demo.fes.entity.File.builder()
                .fileName(fileName)
                .filePath(UPLOADED_FOLDER+id+"_"+fileName)
                .size(size)
                .fileType(fileType)
                .date(LocalDate.now().format(formatters))
                .users(userSet)
                .build();
    }
}
