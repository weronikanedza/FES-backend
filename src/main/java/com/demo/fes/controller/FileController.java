package com.demo.fes.controller;

import com.demo.fes.exception.OperationException;
import com.demo.fes.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping
public class FileController {
    private FileService fileService;

    @Autowired
    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    @RequestMapping(path = "/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("size") String size,
                             @RequestParam("id") String id) throws OperationException, IOException {
        fileService.uploadFile(file,size,Long.valueOf(id));
        return  null;
    }
}
