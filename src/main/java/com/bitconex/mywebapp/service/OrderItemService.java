package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Order;
import com.bitconex.mywebapp.model.OrderItem;
import com.bitconex.mywebapp.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderItemService {
    @Autowired
    OrderItemRepository oir;

    public List<OrderItem> listAll() {
        return (List<OrderItem>)  oir.findAll();
    }
}
