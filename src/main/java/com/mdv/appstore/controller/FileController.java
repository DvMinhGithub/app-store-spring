package com.mdv.appstore.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mdv.appstore.dto.response.ApiResponse;
import com.mdv.appstore.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${app.api.base-url}/files")
public class FileController {
    private final FileService fileService;
    private static final String FILE_UPLOAD_SUCCESS = "File uploaded successfully";
    private static final String FILE_DELETE_SUCCESS = "File deleted successfully";

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String url = fileService.uploadSingleFile(file);
        return ApiResponse.success(url, FILE_UPLOAD_SUCCESS);
    }

    @PostMapping(path = "/upload-multi", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<List<String>> uploadFiles(@RequestParam("files") List<MultipartFile> files) throws IOException {
        List<String> urls = fileService.uploadMultipleFiles(files);
        return ApiResponse.success(urls, FILE_UPLOAD_SUCCESS);
    }

    @DeleteMapping("/{url}")
    public ApiResponse<Void> deleteFile(@PathVariable("url") String url) throws IOException {
        fileService.deleteFileByUrl(url);
        return ApiResponse.success(FILE_DELETE_SUCCESS);
    }
}
