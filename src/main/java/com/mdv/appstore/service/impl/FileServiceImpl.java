package com.mdv.appstore.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mdv.appstore.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class FileServiceImpl implements FileService {

    @NonFinal
    @Value("${file.upload-dir}")
    protected String uploadDir;

    @NonFinal
    @Value("${server.url}")
    private String serverUrl;

    @NonFinal
    @Value("${file.image.allowed-extensions}")
    private String allowedExtensionsString;

    @NonFinal
    @Value("${file.image.path-prefix}")
    private String imagePathPrefix;

    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file.");
        }

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = validateAndGetExtension(originalFilename);
        String uniqueFileName = UUID.randomUUID() + "." + fileExtension;

        Path targetLocation = uploadPath.resolve(uniqueFileName);

        if (!targetLocation.normalize().startsWith(uploadPath)) {
            throw new IOException("Invalid file path: " + targetLocation);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        }

        return String.format("%s/image/%s", serverUrl, uniqueFileName);
    }

    private String validateAndGetExtension(String filename) throws IOException {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IOException("Filename cannot be empty");
        }

        String normalizedName = filename.trim();
        int lastDotIndex = normalizedName.lastIndexOf('.');

        if (lastDotIndex <= 0) {
            throw new IOException("Filename must have an extension");
        }

        if (lastDotIndex == normalizedName.length() - 1) {
            throw new IOException("File extension cannot be empty");
        }

        String extension = normalizedName.substring(lastDotIndex + 1).toLowerCase();
        Set<String> allowedExtensions = Arrays.stream(allowedExtensionsString.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        if (!allowedExtensions.contains(extension)) {
            throw new IOException(
                    "File extension " + extension + " is not supported. Only accepts: " + allowedExtensions);
        }

        return extension;
    }
}
