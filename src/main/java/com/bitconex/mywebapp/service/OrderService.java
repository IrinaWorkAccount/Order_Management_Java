package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Order;
import com.bitconex.mywebapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * A class that implements the order process and includes functions to create orders, display the order history of a customer and confirm the order.
 */
@Service
public class OrderService {
    @Autowired
    OrderRepository or;

    public void save(Order order) {
        or.save(order);
    }
    public List<Order> listAll(){
        return (List<Order>) or.findAll();
    }

    public Order create() {
        Order newOrder = new Order();
        newOrder.setUser(newOrder.getUser());
        newOrder.setOrderItem(newOrder.getOrderItem());
        return or.save(newOrder);
    }

    public void delete(Long id) {
        Optional<Order> existingOrder= or.findById(id);
        if(existingOrder.isPresent()){
            or.deleteById(id);
        } else {
            throw new IllegalArgumentException("Order with ID " + id + " not found.");
        }
    }

}
