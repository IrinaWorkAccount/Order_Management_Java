package com.bitconex.mywebapp.controller;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.Order;
import com.bitconex.mywebapp.model.Product;
import com.bitconex.mywebapp.repository.CustomerRepository;
import com.bitconex.mywebapp.repository.OrderRepository;
import com.bitconex.mywebapp.repository.ProductRepository;
import com.bitconex.mywebapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(
            @RequestParam Long customerId,
            @RequestParam Long productId,
            @RequestParam int quantity,
            @RequestParam String status) {
        try {
            Optional<Customer> customerOptional = customerRepository.findById(customerId);

            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();

                Optional<Product> productOptional = productRepository.findById(productId);

                if (productOptional.isPresent()) {
                    Product product = productOptional.get();

                        orderService.create(customer, quantity, product, status);
                        return new ResponseEntity<>("Order created successfully.", HttpStatus.CREATED);

                } else {
                    System.out.println("Product with ID " + productId + " not found.");
                }
            } else {
                System.out.println("Customer with ID " + customerId + " not found.");
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating order: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        Optional<Order>orderOptional=orderRepository.findById(orderId);
        try {
            if (orderOptional.isPresent()) {
                orderService.delete(orderId);
                return new ResponseEntity<>("Order deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Order with ID " + orderId + " not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting order: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}








