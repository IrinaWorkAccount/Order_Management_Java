package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.Order;
import com.bitconex.mywebapp.model.Product;
import com.bitconex.mywebapp.model.User;
import com.bitconex.mywebapp.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * A class that implements the order process and includes functions to create orders, display the order history of a customer and confirm the order.
 */
@Service
public class OrderService {

    // ==============
    // PRIVATE FIELDS
    // ==============
    @Autowired
    private OrderRepository or;

    // ==============
    // PUBLIC METHODS
    // ==============

    /**
     * Saves an order in the database.
     */
    public void save(Order order) {
        or.save(order);
    }

    /**
     * Retrieves all orders from the database.
     */
    public List<Order> listAll() {
        return (List<Order>) or.findAll();
    }

    /**
     * Searches for an order by its ID and returns it.
     */
    public Order find(Long id) {
        Optional<Order> existingOrder = or.findById(id);
        if (existingOrder.isPresent()) {
            or.findById(id);
        } else {
            throw new IllegalArgumentException("Order with ID " + id + " not found.");
        }
        return null;
    }

    public Optional<Order> findOrdersById(Long orderId) {
        return or.findById(orderId);
    }

    public Optional<Order> getAllOrdersForUser(Long orderId) {
        return or.findById(orderId);
    }

    /**
     * Creates a new order with the specified parameters and saves it in the database.
     */
    public Order create(Customer customer, int quantity, Product product, String status) {
        Order newOrder = new Order();
        newOrder.setUser(customer);
        newOrder.setQuantity(quantity);
        newOrder.setProduct(product);
        newOrder.setStatus(status);
        or.save(newOrder);
        return newOrder;
    }

    /**
     * Confirms an order and change the status to "confirmed".
     */
    public void confirmOrder(Order order) {
        order.setStatus("confirmed");
        or.save(order);
    }

    /**
     * Calculates the total price.
     */
    /* private double calculateTotalPrice(Order order) {

        List<Product> products = order.getProducts();

        double totalPrice = 0.0;
        for (Product product : products) {
            totalPrice += product.getProductSalePrice() * order.getQuantity();
        }

        return totalPrice;
    }

     */

    /**
     * Deletes an order by its ID from the database.
     */
    public void delete(Long id) {
        Optional<Order> existingOrder = or.findById(id);
        if (existingOrder.isPresent()) {
            or.deleteById(id);
        } else {
            throw new IllegalArgumentException("Order with ID " + id + " not found.");
        }
    }

    /**
     * Converts all orders into JSON format.
     */
    @Transactional
    public String convertOrdersToJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Iterable<Order> orderList = or.findAll();
        return objectMapper.writeValueAsString(orderList);
    }


    public Optional<Order> getOrdersByUserId(Long userId) {
        return or.findById(userId);
    }
    @Transactional
    public String getOrdersJsonForUser(Long userId) throws JsonProcessingException {
        Optional<Order> order = getOrdersByUserId(userId);
        if (order.isPresent()) {
            String json = orderToJson(order.get());
            return json;
        } else {
            return "No orders found for the user";
        }
    }
    private String orderToJson(Order order) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(order);
    }
    }


