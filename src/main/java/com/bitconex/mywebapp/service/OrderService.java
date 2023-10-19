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
import java.util.List;
import java.util.Optional;

/**
 * The `OrderService` class provides methods for managing orders in the system, such as creating, retrieving, and updating orders. It also includes functionality for calculating the total price of a user's orders, fetching orders in JSON format, and confirming orders.
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
     * Saves the given order to the database.
     *
     * @param order The order to be saved.
     */
    public void save(Order order) {
        or.save(order);
    }

    /**
     * Retrieves a list of all orders from the database.
     *
     * @return A list of all orders in the database.
     */
    public List<Order> listAll() {
        return (List<Order>) or.findAll();
    }

    /**
     * Searches for an order by its ID and returns it.
     *
     * @param id The ID of the order to find.
     * @return The found order or `null` if not found.
     * @throws IllegalArgumentException if the order with the given ID is not found.
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

    /**
     * Creates a new order with the specified parameters and saves it in the database.
     *
     * @param customer The customer who placed the order.
     * @param quantity The quantity of the product in the order.
     * @param product  The product included in the order.
     * @param status   The status of the order (e.g., "In Progress").
     * @return The newly created order.
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
     * Calculates the total price of all "In Progress" orders for a user.
     *
     * @param user The user for whom to calculate the total price.
     * @return The calculated total price of the user's orders.
     */
    public double calculateTotalPrice(User user) {
        List<Order> userOrders = findLastOrderForUser(user);

        double totalPrice = 0.0;

        for (Order order : userOrders) {
            double orderTotal = order.getProduct().getProductSalePrice() * order.getQuantity();
            totalPrice += orderTotal;
        }

        return totalPrice;
    }

    /**
     * Finds all "In Progress" orders for a user.
     *
     * @param user The user for whom to find orders.
     * @return A list of "In Progress" orders for the user.
     */
    public List<Order> findLastOrderForUser(User user) {
        return or.findAllByUserAndStatus(user, "In Progress");
    }


    /**
     * Deletes an order by its ID from the database.
     *
     * @param id The ID of the order to be deleted.
     * @throws IllegalArgumentException if the order with the given ID is not found.
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
     *
     * @return A JSON representation of all orders in the database.
     * @throws JsonProcessingException if there is an issue converting orders to JSON.
     */
    @Transactional
    public String convertOrdersToJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Iterable<Order> orderList = or.findAll();
        return objectMapper.writeValueAsString(orderList);
    }

    /**
     * Retrieves orders for a specific user in JSON format.
     *
     * @param user The user for whom to fetch orders.
     * @return A JSON representation of the user's orders.
     * @throws JsonProcessingException if there is an issue converting orders to JSON.
     */
    @Transactional
    public String getOrdersJsonForUser(User user) throws JsonProcessingException {
        Iterable<Order> userOrders = or.findAllByUser(user);
        if (userOrders.iterator().hasNext()) {
            System.out.println("Orders for user " + user + " in JSON format:");
            List<Order> orderJsonList = new ArrayList<>();
            for (Order order : userOrders) {
                orderJsonList.add(order);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(orderJsonList);
        } else {
            return "No orders found for the user";
        }
    }

}


