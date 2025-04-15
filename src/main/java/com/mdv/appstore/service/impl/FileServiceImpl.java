package com.mdv.appstore.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import com.mdv.appstore.service.FileService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class FileServiceImpl implements FileService {

    @NonFinal
    @Value("${file.upload-dir}")
    protected String uploadDir;

    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file.");
        }
        if (Files.notExists(Paths.get(uploadDir))) {
            Files.createDirectories(Paths.get(uploadDir));
        }
        String uniqueFileName = UUID.randomUUID() + getExtension(file.getOriginalFilename());
        Path path = Paths.get(uploadDir + uniqueFileName);
        Files.write(path, file.getBytes());
        return path.toString();
    }

    private String getExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i);
        }
        return extension;
    }
}
