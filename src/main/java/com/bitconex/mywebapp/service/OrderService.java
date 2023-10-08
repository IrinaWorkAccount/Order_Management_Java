package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.Order;
import com.bitconex.mywebapp.model.Product;
import com.bitconex.mywebapp.model.User;
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

    public Order find(Long id) {
        Optional<Order> existingOrder= or.findById(id);
        if(existingOrder.isPresent()){
            or.findById(id);
        } else {
            throw new IllegalArgumentException("Order with ID " + id + " not found.");
        }
        return null;
    }

/*    public List<Order> findOrdersById(Long orderId) {
        return or.findById(orderId);
    }

    public List<Order> getAllOrdersForUser(Long orderId) {
        return or.findById(orderId);
    }*/

    public Order create(Customer customer, int quantity, Product product, String status) {
        Order newOrder = new Order();
        newOrder.setUser(customer);
        newOrder.setQuantity(quantity);
        newOrder.setProduct(product);
        newOrder.setStatus(status);

        return or.save(newOrder);
    }

    public double confirmOrder(Order order) {
        double totalPrice = calculateTotalPrice(order);
        order.setStatus("bestätigt");
        or.save(order);
        return totalPrice;
    }

    private double calculateTotalPrice(Order order) {

        List<Product> products = order.getProducts();

        double totalPrice = 0.0;
        for (Product product : products) {
            totalPrice += product.getProductSalePrice() * order.getQuantity();
        }

        return totalPrice;
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

