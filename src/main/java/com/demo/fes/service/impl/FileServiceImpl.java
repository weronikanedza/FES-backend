package com.demo.fes.service.impl;


import com.demo.fes.definitions.ErrorMessages;
import com.demo.fes.entity.File;
import com.demo.fes.entity.User;
import com.demo.fes.exception.OperationException;
import com.demo.fes.repository.FileRepository;
import com.demo.fes.repository.UserRepository;
import com.demo.fes.service.FileService;
import com.demo.fes.service.helper.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {
    private UserRepository userRepository;
    private FileRepository fileRepository;
    private static final String SEPARATOR = java.io.File.separator;
    private static final String UPLOADED_FOLDER = "files" + SEPARATOR;
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d-MM-uuuu");
    private EmailService emailService;

    @Autowired
    public FileServiceImpl(EmailService emailService,UserRepository userRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.emailService=emailService;
    }

    @Override
    public com.demo.fes.entity.File uploadFile(MultipartFile file, String size, Long id) throws OperationException, IOException {
        String fileName = file.getOriginalFilename();
        String fileType = getFileType(file);
        checkFile(file, id, fileType);

        com.demo.fes.entity.File newFile = createFile(file, fileName, fileType, size, id);
        Optional<User> user = userRepository.findById(id);
        newFile.getUsers().add(user.get());
        user.get().getFiles().add(newFile);
        return fileRepository.save(newFile);
    }

    @Override
    public Set<File> getAllFiles(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.get().getFiles();
    }

    @Override
    public Set<File> getAllUserFiles(Long id) {
        User user = userRepository.findById(id).orElse(null);
        Set<File> filesAddedByUser = user.getFiles()
                .stream()
                .filter(file -> file.getAdderId() == id)
                .collect(Collectors.toSet());

        return filesAddedByUser;
    }


    @Override
    public Set<File> getAllSharedFiles(Long id) {
        User user = userRepository.findById(id).orElse(null);
        Set<File> filesAddedByUser = user.getFiles()
                .stream()
                .filter(file -> file.getAdderId() != id)
                .collect(Collectors.toSet());

        return filesAddedByUser;
    }

    @Override
    public Optional<File> getFileById(Long id) {
        return fileRepository.findById(id);
    }

    @Override
    public void deleteFile(Long fileId,Long userId) {
        File file = fileRepository.findById(fileId).orElse(null);
        Set<User> users = file.getUsers();
        User currentUser = userRepository.getOne(userId);

        if (userId == file.getAdderId()) {
            for (User user : users) {
                user.getFiles().remove(file);
            }
            fileRepository.deleteById(fileId);
        }else{
            currentUser.getFiles().remove(file);
            file.getUsers().remove(currentUser);
            userRepository.save(currentUser);
        }

    }

    @Override
    public void shareFile(Long id, String email) throws OperationException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new OperationException(HttpStatus.NOT_ACCEPTABLE, ErrorMessages.USER_IS_NOT_IN_DB);
        }

        File file = fileRepository.getOne(id);
        if (file.getUsers().contains(user)) {
            throw new OperationException(HttpStatus.NOT_ACCEPTABLE, ErrorMessages.FILE_IS_SHARED);
        }

        user.getFiles().add(file);
        file.getUsers().add(user);
        userRepository.save(user);

        emailService.sendSimpleMessage(email,"Udostępniony plik","Został ci udostępniony plik : "+ file.getFileName());
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

        if (file.getSize() > 10485760) {
            throw new OperationException(HttpStatus.NOT_ACCEPTABLE, ErrorMessages.FILE_SIZE);
        }
        if (!(fileType.equals("pdf") || fileType.equals("txt") || fileType.equals("jpg") || fileType.equals("docx")
                || fileType.equals("png"))) {
            throw new OperationException(HttpStatus.NOT_ACCEPTABLE, ErrorMessages.NOT_ACCEPTABLE_FILE_FORMAT);
        }
//        if(isFileWithGivenName(files,fileName)){
//            throw new OperationException(HttpStatus.NOT_ACCEPTABLE, ErrorMessages.FILE_EXISTS);
//        }
    }

    private boolean isFileWithGivenName(Set<File> files, String fileName) {
        return files.stream().anyMatch(f -> f.getFileName().equals(fileName));
    }

    private com.demo.fes.entity.File createFile(MultipartFile file, String fileName, String fileType, String size, Long id) throws IOException {
        Set<User> userSet = new HashSet<>();
        userSet.add(userRepository.findById(id).get());

        return com.demo.fes.entity.File.builder()
                .fileName(fileName)
                .filePath(UPLOADED_FOLDER + id + "_" + fileName)
                .size(size)
                .fileType(fileType)
                .date(LocalDate.now().format(formatters))
                .users(userSet)
                .adderId(id)
                .contentType(file.getContentType())
                .data(file.getBytes())
                .build();
    }
}
