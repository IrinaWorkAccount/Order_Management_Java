package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.Order;
import com.bitconex.mywebapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface OrderRepository extends CrudRepository<Order, Long> {
        long countById(Long id);

    List<Order> findById(User id);
    }