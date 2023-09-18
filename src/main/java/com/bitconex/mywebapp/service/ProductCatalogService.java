package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Product;
import com.bitconex.mywebapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * A class that implements product catalog functions, such as adding, listing and deleteing products.
 */

public class ProductCatalogService {

    @Autowired
    private ProductRepository repo;

    public Product addProduct(Product product) {

        product.setProductName(product.getProductName());
        product.setProductSalePrice(product.getProductSalePrice());
        product.setProductAvailableFrom(product.getProductAvailableFrom());
        product.setProductAvailableUntil(product.getProductAvailableUntil());
        product.setProductQuantity(product.getProductQuantity());

        return repo.save(product);

    }

    public List<Product> getAllProducts() {
        return (List<Product>) repo.findAll();
    }


    public void deleteProduct(Long id) {
        Optional<Product> existingProduct = repo.findById(id);
        if (existingProduct.isPresent()) {
            repo.deleteById(id);
        } else {
            throw new IllegalArgumentException("Product with ID " + id + " not found.");
        }
    }
}
