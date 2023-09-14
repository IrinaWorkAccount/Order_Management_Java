package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
long countById(Long id);

}
