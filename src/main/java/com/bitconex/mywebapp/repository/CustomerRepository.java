package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the entity Customer.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //long countById(Long id);
    // Optional<Customer> findByUserLogin(String userLogin);
}
