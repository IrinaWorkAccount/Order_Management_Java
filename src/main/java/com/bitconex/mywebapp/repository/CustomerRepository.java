package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The `CustomerRepository` is a repository interface for the `Customer` entity. It provides access to basic CRUD (Create, Read, Update, Delete) operations for `Customer` entities.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // No additional methods are defined in this repository.
}
