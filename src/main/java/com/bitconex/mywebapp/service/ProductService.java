package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Product;
import com.bitconex.mywebapp.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The `ProductService` class provides methods for managing the product catalog in the system, including adding, listing, updating, and deleting products. It also offers functionality to retrieve a list of all products in JSON format.
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
     *
     * @param product The product to be added to the catalog.
     * @return The newly added product with generated ID.
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
     *
     * @param product The product to be updated.
     */
    public void save(Product product) {
        pr.save(product);
    }

    /**
     * Retrieves a list of all products from the product database.
     *
     * @return A list of all products in the catalog.
     */
    public List<Product> listAll() {
        return (List<Product>) pr.findAll();
    }

    /**
     * Deletes a product by its ID from the database.
     *
     * @param id The ID of the product to be deleted.
     * @throws IllegalArgumentException if the product with the given ID is not found.
     */
    public void delete(Long id) {
        Optional<Product> existingProduct = pr.findById(id);
        if (existingProduct.isPresent()) {
            pr.deleteById(id);
        } else {
            throw new IllegalArgumentException("Product with ID " + id + " not found.");
        }
    }

    /**
     * Retrieves a list of all products in JSON format.
     *
     * @return A JSON representation of all products in the catalog.
     * @throws JsonProcessingException if there is an issue converting products to JSON.
     */
    @Transactional
    public String listAllProductsInJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Disable the default date format
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Iterable<Product> productList = pr.findAll();
        return objectMapper.writeValueAsString(productList);
    }

}
