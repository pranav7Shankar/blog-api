package com.webApp.bloggingapp.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {
    String uploadImage(MultipartFile file) throws IOException;
    String getResource(String publicId);
}
