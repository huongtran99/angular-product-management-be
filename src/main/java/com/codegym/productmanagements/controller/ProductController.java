package com.codegym.productmanagements.controller;

import com.codegym.productmanagements.model.Product;
import com.codegym.productmanagements.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll(@RequestParam("q") Optional<String> search, @PageableDefault(size = 3) Pageable pageable) {
        if(search.isPresent()) {
            return new ResponseEntity<>(productService.findProductByNameContaining(search.get(), pageable), HttpStatus.OK);
        }
        return new ResponseEntity<>(productService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> findOneProduct(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        return productOptional.map(product -> new ResponseEntity<>(product, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> editProduct(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> productOptional = productService.findById(id);
        if(!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(product.getId() != null) {
            product.setId(id);
        }
        return new ResponseEntity<>(productService.save(product), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id){
        Optional<Product> productOptional = productService.findById(id);
        if(!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.remove(id);
        return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
    }

}
