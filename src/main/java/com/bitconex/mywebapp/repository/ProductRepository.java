package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    long countById(Long id);
}
