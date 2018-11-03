package com.demo.fes.service.impl;


import com.demo.fes.definitions.ErrorMessages;
import com.demo.fes.entity.File;
import com.demo.fes.entity.User;
import com.demo.fes.exception.OperationException;
import com.demo.fes.repository.FileRepository;
import com.demo.fes.repository.UserRepository;
import com.demo.fes.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private static final String SEPARATOR = java.io.File.separator;
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
        String workingDir = System.getProperty("user.dir");
        checkFile(file,id,fileType);
        saveFileInFolder(file, id);

        com.demo.fes.entity.File newFile = createFile(file,fileName,fileType,size,id);
        Optional<User> user = userRepository.findById(id);
        newFile.getUsers().add(user.get());
        user.get().getFiles().add(newFile);
        return fileRepository.save(newFile);
    }

    @Override
    public Set<File> getAllFiles(Long id) {
        Optional<User> user = userRepository.findById(id);
        return  user.get().getFiles();
    }

    @Override
    public Optional<File> getFileById(Long id) {
        return fileRepository.findById(id);
    }

    private String getFileType(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf(".") + 1).trim();
    }

    private void checkFile(MultipartFile file, Long id, String fileType) throws OperationException, IOException {
        String fileName = file.getOriginalFilename();
        if (file.isEmpty())
            throw new OperationException(HttpStatus.NOT_ACCEPTABLE, ErrorMessages.EMPTY_FILE);

        Optional<User> user = userRepository.findById(id);
        Set<File> files = user.get().getFiles();

        if(file.getSize()> 10485760){
            throw new OperationException(HttpStatus.NOT_ACCEPTABLE, ErrorMessages.FILE_SIZE);
        }
        if(!(fileType.equals("pdf") || fileType.equals("txt") || fileType.equals("jpg")  || fileType.equals("docx")
        || fileType.equals("png"))){
            throw new OperationException(HttpStatus.NOT_ACCEPTABLE, ErrorMessages.NOT_ACCEPTABLE_FILE_FORMAT);
        }
        if(isFileWithGivenName(files,fileName)){
            throw new OperationException(HttpStatus.NOT_ACCEPTABLE, ErrorMessages.FILE_EXISTS);
        }
    }

    private boolean isFileWithGivenName(Set<File> files, String fileName){
        return files.stream().anyMatch(f ->f.getFileName().equals(fileName));
    }
    private void saveFileInFolder(MultipartFile file, Long id) throws IOException {
        String fileName = file.getOriginalFilename();
        Files.write(Paths.get(UPLOADED_FOLDER + id + "_" + fileName), file.getBytes());
    }

    private com.demo.fes.entity.File createFile(MultipartFile file ,String fileName, String fileType, String size, Long id) throws IOException {
        Set<User> userSet = new HashSet<>();
        userSet.add(userRepository.findById(id).get());

        return  com.demo.fes.entity.File.builder()
                .fileName(fileName)
                .filePath(UPLOADED_FOLDER+id+"_"+fileName)
                .size(size)
                .fileType(fileType)
                .date(LocalDate.now().format(formatters))
                .users(userSet)
                .contentType(file.getContentType())
                .data(file.getBytes())
                .build();
    }
}
