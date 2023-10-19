package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The `ProductRepository` is a repository interface for the `Product` entity. It provides access to basic CRUD (Create, Read, Update, Delete) operations for `Product` entities.
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    // No additional custom methods defined in this repository.
}
