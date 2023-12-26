package org.example.service.imp;

import org.example.error.IllegalArgumentException;
import org.example.error.NotFoundException;
import org.example.model.Image;
import org.example.repository.ImageRepository;
import org.example.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImp implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Value("${app.image.basePath}")
    private String basePath;

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            // Generate a unique filename for the image, for example, using UUID
            String filename = UUID.randomUUID().toString() + "." + getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));

            // Save the file to the base directory
//            Path imagePath = Paths.get(basePath, filename);
//            Files.write(imagePath, file.getBytes());

            // Save Img to DB
            Image image = new Image(null, filename, null, file.getBytes());
            image.setImageData(file.getBytes());
            imageRepository.save(image);

            // Return the relative path to the image (e.g., "/images/filename.jpg")
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to get the file extension from the original filename
    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }


    @Override
    public byte[] downloadImage(String fileName) {
        if (!existsByName(fileName)) {
            throw new NotFoundException(String.format("There Is No Record In Our Database Match That Name: %s ", fileName));
        }
        Optional<Image> dbImageData = imageRepository.findByName(fileName);
        byte[] image = dbImageData.get().getImageData();
        return image;
    }

    @Override
    public void deleteByName(String name) {
        if (existsByName(name)) {
           Image image =  imageRepository.findByName(name).get();
           imageRepository.delete(image);
        }else{
            throw new NotFoundException(String.format("There Is No Record In Our Database Match That Name: %s ", name));
        }
    }

    private boolean existsByName(String name) {
        return imageRepository.existsByName(name);
    }
}
