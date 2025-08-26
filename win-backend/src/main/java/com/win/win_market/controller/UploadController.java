package com.win.win_market.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class UploadController {

    private static final String UPLOAD_DIR = "src/main/resources/uploads/";

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, uniqueFileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath);

            return "/uploads/" + uniqueFileName;
        } catch (Exception e) {
            return "Erro ao fazer upload: " + e.getMessage();
        }
    }

    @GetMapping("/get")
    public ResponseEntity<byte[]> getImage(String imagePath) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR, imagePath);
            byte[] imageBytes = Files.readAllBytes(filePath);
            return ResponseEntity.ok().body(imageBytes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
