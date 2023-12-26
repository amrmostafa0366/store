package org.example.service.imp;

import jakarta.transaction.Transactional;
import org.example.error.ConflictException;
import org.example.error.IllegalArgumentException;
import org.example.error.NotFoundException;
import org.example.model.Image;
import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.example.service.ImageService;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageService imageService;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        if (!existsById(id)) {
            throw new NotFoundException(String.format("There Is No Record In Our Database Match That Id: %s ", id));
        }
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> findByName(String name) {
        return productRepository.findByNameContains(name);
    }

    @Override
    public Product add(Product product) {
        if (existsByName(product.getName())) {
            throw new ConflictException(String.format("There Is A Record In Our Database With The same Name: %s ", product.getName()));
        } else if (product.getName().isBlank() || product.getName() == null) {
            throw new IllegalArgumentException("Name Can't Be Empty or Null ");
        }
        return productRepository.save(product);
    }

    @Override
    public Product addImageToProduct(Long id, MultipartFile file) {
        if (!existsById(id)) {
            throw new NotFoundException(String.format("There Is No Record In Our Database Match That Id: %s ", id));
        }
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image cannot be empty or null.");
        }
        Product product = productRepository.findById(id).get();
        String imgUrl = imageService.uploadImage(file);
        if (imgUrl != null) {
            product.setImgUrl(imgUrl);
            return productRepository.save(product);
        }
        return null;
    }
    @Override
    public byte[] findImageByName(String fileName) {
        byte[] image = imageService.downloadImage(fileName);
        return image;
    }


    @Override
    public Product update(Long id, Product product) {
        if (!existsById(id)) {
            throw new NotFoundException(String.format("There Is No Record In Our Database Match That Id: %s ", id));
        } else if (existsByName(product.getName())) {
            throw new ConflictException(String.format("There Is A Record In Our Database With The same Name: %s ", product.getName()));
        } else if (product.getName().isBlank() || product.getName() == null) {
            throw new IllegalArgumentException("Name Can't Be Empty or Null ");
        }
        Product dbProduct = productRepository.findById(id).get();
        dbProduct.setName(product.getName());
        return productRepository.save(dbProduct);
    }


    @Transactional
    @Override
    public void deleteById(Long id) {
        if (existsById(id)) {
            Product product = productRepository.findById(id).get();
            imageService.deleteByName(product.getImgUrl());
            productRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format("There Is No Record In Our Database Match That Id: %s ", id));
        }
    }

    private boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    private boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

}
