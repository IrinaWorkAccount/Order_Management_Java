package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Product;
import com.bitconex.mywebapp.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A class that implements product catalog functions, such as adding, listing and deleteing products.
 */
@Service
public class ProductService {

    // ==============
    // PRIVATE FIELDS
    // ==============

    @Autowired
    private ProductRepository pr;

    // ==============
    // PUBLIC METHODS
    // ==============

    /**
     * Adds a new product to the product database.
     */
    public Product add(Product product) {

        product.setProductName(product.getProductName());
        product.setProductSalePrice(product.getProductSalePrice());
        product.setProductAvailableFrom(product.getProductAvailableFrom());
        product.setProductAvailableUntil(product.getProductAvailableUntil());
        product.setProductQuantity(product.getProductQuantity());

        return pr.save(product);

    }

    /**
     * Updates an existing product in the database.
     */
    public void save(Product product) {
        pr.save(product);
    }

    /**
     * Retrieves all products from the product database.
     */
    public List<Product> listAll() {
        return (List<Product>) pr.findAll();
    }

    /**
     * Deletes a product by its ID from the database.
     */
    public void delete(Long id) {
        Optional<Product> existingProduct = pr.findById(id);
        if (existingProduct.isPresent()) {
            pr.deleteById(id);
        } else {
            throw new IllegalArgumentException("Product with ID " + id + " not found.");
        }
    }
    @Transactional
    public String listAllProductsInJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Iterable<Product> productList = pr.findAll();
        List<Product>target=new ArrayList<>();
        productList.forEach(target::add);
        return objectMapper.writeValueAsString(productList);
    }
}
