//package com.ung.apiboard.service;
//
//import com.ung.apiboard.exception.FileStorageException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.UUID;
//
//@Component
//public class FileStorageService {
//
//    @Value("${file.upload-dir}")
//    private String uploadDir;
//    public String storeFile(MultipartFile file) {
//        String fileName = generateFileName(file);
//        Path uploadPath = Paths.get(uploadDir);
//
//        try {
//            String filePath = uploadDir + File.separator + file.getOriginalFilename();
//            File dest = new File(filePath);
//            file.transferTo(dest);
//            return fileName;
//        } catch (IOException e) {
//            throw new FileStorageException("파일 저장에 실패했습니다.", e);
//        }
//    }
//    private String generateFileName(MultipartFile file) {
//        return UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//    }
//}
