package com.bitconex.mywebapp.controller;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.Order;
import com.bitconex.mywebapp.model.Product;
import com.bitconex.mywebapp.model.User;
import com.bitconex.mywebapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;


}
