package com.bitconex.mywebapp.controller;

import com.bitconex.mywebapp.model.Product;
import com.bitconex.mywebapp.model.User;
import com.bitconex.mywebapp.repository.CustomerRepository;
import com.bitconex.mywebapp.repository.ProductRepository;
import com.bitconex.mywebapp.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAll() {
        List<Product> listProducts = productService.listAll();
        return ResponseEntity.ok(listProducts);
    }


    @GetMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok( "The product" + id + " has been deleted. ");
    }

   // @GetMapping("/")
}