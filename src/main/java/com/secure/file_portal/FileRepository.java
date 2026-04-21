package com.secure.file_portal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<SecureFile, Long> {
    // Ye line magic hai! Spring khud SQL query likh dega
    SecureFile findByFileName(String fileName);
}