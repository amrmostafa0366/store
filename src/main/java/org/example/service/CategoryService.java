package org.example.service;

import org.example.model.Category;
import org.example.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> findAll();

    Category findById(Long id);

    List<Category> findByName(String name);

    Category add(Category category);

    void deleteById(Long id);

    Category update(Long id, Category category);

    Category addProductToCategory(Long id, Product product);
}
