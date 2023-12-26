package org.example.contoller;

import org.example.model.Category;
import org.example.model.Product;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> findAll() {
        List<Category> result = categoryService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Category result = categoryService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Category>> findByName(@RequestParam(value = "name") String name) {
        List<Category> result = categoryService.findByName(name);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Category> add(@RequestBody Category category) {
        Category result = categoryService.add(category);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable(value = "id") Long id, @RequestBody Category category) {
        Category result = categoryService.update(id, category);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(value = "id") Long id) {
        categoryService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/{id}/product")
    public ResponseEntity<Category> addProductToCategory(@PathVariable(value = "id") Long id ,@RequestBody Product product){
        Category result = categoryService.addProductToCategory(id,product);
        return new ResponseEntity<Category>(result,HttpStatus.CREATED);
    }

}
