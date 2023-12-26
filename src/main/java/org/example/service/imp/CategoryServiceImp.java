package org.example.service.imp;

import org.example.error.ConflictException;
import org.example.error.IllegalArgumentException;
import org.example.error.NotFoundException;
import org.example.model.Category;
import org.example.model.Product;
import org.example.repository.CategoryRepository;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        if (!existsById(id)) {
            throw new NotFoundException(String.format("There Is No Record In Our Database Match That Id: %s ", id));
        }
        return categoryRepository.findById(id).get();
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryRepository.findByNameContains(name);
    }

    @Override
    public Category add(Category category) {
        if (existsByName(category.getName())) {
            throw new ConflictException(String.format("There Is A Record In Our Database With The same Name: %s ", category.getName()));
        } else if (category.getName().isBlank() || category.getName() == null) {
            throw new IllegalArgumentException("Name Can't Be Empty or Null ");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        if (!existsById(id)) {
            throw new NotFoundException(String.format("There Is No Record In Our Database Match That Id: %s ", id));
        } else if (existsByName(category.getName())) {
            throw new ConflictException(String.format("There Is A Record In Our Database With The same Name: %s ", category.getName()));
        } else if (category.getName().isBlank() || category.getName() == null) {
            throw new IllegalArgumentException("Name Can't Be Empty or Null ");
        }
        Category dbCategory = categoryRepository.findById(id).get();
        dbCategory.setName(category.getName());
        return categoryRepository.save(dbCategory);
    }

    @Override
    public Category addProductToCategory(Long id, Product product) {
        if (!existsById(id)) {
            throw new NotFoundException(String.format("There Is No Record In Our Database Match That Id: %s ", id));
        }
        if (product.getName().isBlank() || product.getName() == null) {
            throw new IllegalArgumentException("Name Can't Be Empty or Null ");
        }
        Category category = categoryRepository.findById(id).get();
        category.addProduct(product);
        return categoryRepository.save(category);
    }
    @Override
    public void deleteById(Long id) {
        if (existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format("There Is No Record In Our Database Match That Id: %s ", id));
        }
    }

    private boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    private boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

}
