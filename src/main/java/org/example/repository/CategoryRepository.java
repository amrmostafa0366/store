package org.example.repository;

import org.example.model.Category;
import org.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByNameContains(String name);

    boolean existsByName(String name);
}
