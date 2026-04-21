package com.secure.file_portal;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileStorageService storageService;

    public FileController(FileStorageService storageService) {
        this.storageService = storageService;
    }

    // MULTIPLE FILES UPLOAD
    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("file") MultipartFile[] files) {
        List<String> responses = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String key = storageService.saveSecurely(file);
                responses.add("Success: " + file.getOriginalFilename() + " | Key: " + key);
            } catch (Exception e) {
                responses.add("Error: " + file.getOriginalFilename() + " -> " + e.getMessage());
            }
        }
        return ResponseEntity.ok(responses);
    }

    // SECURE DOWNLOAD & DECRYPT
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            SecureFile secureFile = storageService.getFileMetadata(fileName);
            if (secureFile == null) return ResponseEntity.notFound().build();

            Resource resource = storageService.loadAndDecrypt(fileName, secureFile.getSecretKey());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}