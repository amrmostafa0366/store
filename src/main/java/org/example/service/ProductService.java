package org.example.service;

import org.example.model.Image;
import org.example.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService {
    List<Product> findAll();

    Product findById(Long id);

    List<Product> findByName(String name);

    Product add(Product product);

    void deleteById(Long id);

    Product addImageToProduct(Long id, MultipartFile file);

    Product update(Long id, Product product);

    byte[] findImageByName(String fileName);
}
