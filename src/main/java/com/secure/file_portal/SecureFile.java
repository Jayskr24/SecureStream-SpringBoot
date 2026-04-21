package com.secure.file_portal;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data // Isse Getter/Setter khud ban jayenge
public class SecureFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @Column(length = 500) // Key thodi lambi hoti hai
    private String secretKey;

    private LocalDateTime uploadDate;

    // Default constructor
    public SecureFile() {}

    // Easy way to create a new record
    public SecureFile(String fileName, String secretKey) {
        this.fileName = fileName;
        this.secretKey = secretKey;
        this.uploadDate = LocalDateTime.now();
    }
}