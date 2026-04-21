**SecureStream** is a robust Spring Boot backend application designed to provide top-tier security for file storage. It uses military-grade AES-256 encryption to ensure that even if a database is compromised, the files remain unreadable and safe.

---

## 🚀 Key Features
* **Dual-Layer Security:** Files are stored on the server, while unique encryption keys are managed separately in an H2 Database.
* **Unique Per-File Keys:** Every single upload generates a new secret key.
* **Zero-Knowledge Storage:** Files on disk are stored as encrypted "Ciphertext" (Gibberish symbols).
* **High Performance:** Efficient streaming encryption that doesn't slow down your system.

---

## 🛠️ Tech Stack
* **Framework:** Spring Boot (Java)
* **Security:** AES-256 (Advanced Encryption Standard)
* **Database:** H2 Database (In-Memory for metadata)
* **API Testing:** Postman

---

## 📂 Project Structure
* `src/main/java`: Contains the core logic for encryption, decryption, and API controllers.
* `pom.xml`: Project dependencies and build configuration.

---

## 📸 Demo Screenshots
*(Add your Postman and H2 Console screenshots here later)*

---

## 🔮 Future Roadmap
- [ ] User Authentication (Login/Signup) using Spring Security.
- [ ] Cloud Integration (AWS S3 / Google Cloud Storage).
- [ ] Mobile App interface for secure uploads on the go.

---

### 👨‍💻 Developed By
**Jayprakash Kumar** *Aspiring Software Developer | Computer Science Student*

---
*Copyright (c) 2026 Jayprakash Kumar. For educational purposes only.*
