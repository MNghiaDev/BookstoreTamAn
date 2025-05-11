package com.minhnghia.datn.BookstoreTamAn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class ImageUploadController {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        // Tạo tên file duy nhất
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        // Trả về đường dẫn ảnh
        String fileUrl = "/uploads/" + fileName;
        return ResponseEntity.ok(fileUrl);
    }
}
