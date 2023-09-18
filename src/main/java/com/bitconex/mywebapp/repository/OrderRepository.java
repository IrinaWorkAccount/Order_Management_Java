package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.Order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

}
