package org.example.contoller;

import org.example.model.Image;
import org.example.model.Product;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> result = productService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product result = productService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Product>> findByName(@RequestParam(value = "name") String name) {
        List<Product> result = productService.findByName(name);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Product> add(@RequestBody Product product) {
        Product result = productService.add(product);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Product> addImageToProduct(@PathVariable Long id, @RequestParam(value = "image") MultipartFile file){
        Product result = productService.addImageToProduct(id,file);
        return new ResponseEntity<>(result,HttpStatus.OK);

    }
    @GetMapping("/image/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName){
        byte[] image = productService.findImageByName(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable(value = "id") Long id, @RequestBody Product product) {
        Product result = productService.update(id, product);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(value = "id") Long id) {
        productService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}