package org.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageService {
    String uploadImage(MultipartFile file);

    byte[] downloadImage(String fileName);

    void deleteByName(String imgUrl);
}
