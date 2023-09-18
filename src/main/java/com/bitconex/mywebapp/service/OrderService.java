package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Order;
import com.bitconex.mywebapp.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * A class that implements the order process and includes functions to create orders, display the order history of a customer and confirm the order.
 */

public class OrderService {
    @Autowired
    private OrderRepository repo;

    /*public List<Order> getAllOrders() {
        return repo.findAll();
    }*/

    public Order createOrder() {
        Order newOrder = new Order();
newOrder.setUser(newOrder.getUser());
newOrder.setProduct(newOrder.getProduct());
        return repo.save(newOrder);
    }

    public void deleteOrder(Long orderId) {
        Optional<Order>existingOrder= repo.findById(orderId);
        if(existingOrder.isPresent()){
            repo.deleteById(orderId);
        } else {
            throw new IllegalArgumentException("Order with ID " + orderId + " not found.");
        }
    }

    /*public Order updateOrder(Long orderId) {
Optional<Order> optionalOrder=repo.findById(orderId);

        return repo.save();
    }*/

    // Weitere Methoden hinzufügen, z.B. Export
}
