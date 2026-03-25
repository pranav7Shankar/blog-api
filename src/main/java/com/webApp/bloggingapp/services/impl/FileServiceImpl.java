package com.webApp.bloggingapp.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.webApp.bloggingapp.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        Map<?, ?> result = cloudinary.uploader().upload(fileBytes, ObjectUtils.emptyMap());

        return result.get("secure_url").toString();
    }

    @Override
    public String getResource(String publicId) {
        return cloudinary.url().generate(publicId);
    }
}
