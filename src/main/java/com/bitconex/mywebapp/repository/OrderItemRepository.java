package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
        long countById(Long id);
}
