package com.mdv.appstore.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadSingleFile(MultipartFile file) throws IOException;

    List<String> uploadMultipleFiles(List<MultipartFile> files) throws IOException;

    void deleteFileByUrl(String fileUrl) throws IOException;
}
