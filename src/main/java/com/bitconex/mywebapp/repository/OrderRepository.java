package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.Order;
import com.bitconex.mywebapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for the entity Order.
 */
@Repository
    public interface OrderRepository extends CrudRepository<Order, Long> {
        Long countById(Long id);

    Optional<Order> findById(Long id);
    }