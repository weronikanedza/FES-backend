package com.demo.fes.controller;

import com.demo.fes.entity.File;
import com.demo.fes.exception.OperationException;
import com.demo.fes.response.FileRs;
import com.demo.fes.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class FileController {
    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(path = "/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("size") String size,
                             @RequestParam("id") String id) throws OperationException, IOException {
        fileService.uploadFile(file, size, Long.valueOf(id));
        return null;
    }

    @RequestMapping(path = "/getAllFiles")
    public ResponseEntity<List<FileRs>> getAllFiles(@RequestBody String id)  {
        id = id.replace("=", "");
        Set<File> files = fileService.getAllFiles(Long.valueOf(id));

        return ResponseEntity.ok(files.stream()
                .map(File::convertTo)
                .collect(Collectors.toList()));
    }


    @RequestMapping(path = "/getAllFilesAddedByUser")
    public ResponseEntity<List<FileRs>> getAllUserFiles(@RequestBody String id)  {
        id = id.replace("=", "");
        Set<File> files = fileService.getAllUserFiles(Long.valueOf(id));

        return ResponseEntity.ok(files.stream()
                .map(File::convertTo)
                .collect(Collectors.toList()));
    }

    @RequestMapping(path = "/getAllSharedFiles")
    public ResponseEntity<List<FileRs>> getAllSharedFiles(@RequestBody String id)  {
        id = id.replace("=", "");
        Set<File> files = fileService.getAllSharedFiles(Long.valueOf(id));

        return ResponseEntity.ok(files.stream()
                .map(File::convertTo)
                .collect(Collectors.toList()));
    }

    @RequestMapping(path = "/downloadFile")
    public ResponseEntity<byte[]> downloadFile(@RequestBody String id)  {
        id = id.replace("=", "");
        File file=fileService.getFileById(Long.valueOf(id)).get();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(file.getContentType()));
        header.setContentLength(file.getData().length);
        header.add("Content-Disposition", "attachment; filename=" + file.getFileName());
        header.set("Content-Disposition", "attachment; filename=" + file.getFileName());
        return new ResponseEntity<>(file.getData(), header, HttpStatus.OK);
    }

    @RequestMapping(path = "/deleteFile")
    public String deleteFile(@RequestParam("fileId") String fileId,@RequestParam("userId") String userId) {
        Long fileIdConv = Long.valueOf(fileId);
        Long userIdConv = Long.valueOf(userId);
        fileService.deleteFile(fileIdConv,userIdConv);
        return null;
    }

    @RequestMapping(path = "/shareFile")
    public String shareFile(@RequestParam("id") String id,@RequestParam("email") String email) throws OperationException {
        Long fileId = Long.valueOf(id);
        fileService.shareFile(fileId,email);
        return null;
    }
}
