package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Product;
import com.bitconex.mywebapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * A class that implements product catalog functions, such as adding, listing and deleteing products.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository pr;

    public Product add(Product product) {

        product.setProductName(product.getProductName());
        product.setProductSalePrice(product.getProductSalePrice());
        product.setProductAvailableFrom(product.getProductAvailableFrom());
        product.setProductAvailableUntil(product.getProductAvailableUntil());
        product.setProductQuantity(product.getProductQuantity());

        return pr.save(product);

    }

    public void save(Product product) {
        pr.save(product);
    }

    public List<Product> listAll() {
        return (List<Product>) pr.findAll();
    }


    public void delete(Long id) {
        Optional<Product> existingProduct = pr.findById(id);
        if (existingProduct.isPresent()) {
            pr.deleteById(id);
        } else {
            throw new IllegalArgumentException("Product with ID " + id + " not found.");
        }
    }
}
