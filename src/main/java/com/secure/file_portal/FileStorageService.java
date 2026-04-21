package com.secure.file_portal;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final Path root = Paths.get("uploads");
    private final FileRepository fileRepository;

    public FileStorageService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void init() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (Exception e) {
            System.err.println("Uploads folder error: " + e.getMessage());
        }
    }

    public String saveSecurely(MultipartFile file) {
        try {
            SecretKey key = CryptoUtils.generateKey();
            String encodedKey = CryptoUtils.keyToString(key);

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            Path targetPath = this.root.resolve(file.getOriginalFilename());

            try (InputStream is = file.getInputStream();
                 OutputStream os = Files.newOutputStream(targetPath);
                 CipherOutputStream cos = new CipherOutputStream(os, cipher)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    cos.write(buffer, 0, read);
                }
            }

            // Save metadata to Database
            SecureFile secureFile = new SecureFile(file.getOriginalFilename(), encodedKey);
            fileRepository.save(secureFile);

            return encodedKey;
        } catch (Exception e) {
            throw new RuntimeException("Encryption fail: " + e.getMessage());
        }
    }

    public SecureFile getFileMetadata(String fileName) {
        return fileRepository.findByFileName(fileName);
    }

    public Resource loadAndDecrypt(String fileName, String encodedKey) throws Exception {
        Path filePath = root.resolve(fileName);
        SecretKey key = CryptoUtils.stringToKey(encodedKey);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] encryptedBytes = Files.readAllBytes(filePath);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new ByteArrayResource(decryptedBytes);
    }
}