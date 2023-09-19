package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.User;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends UserService{

    @Override
    public void save(User user) {
        if (user instanceof Customer) {
        }
        super.save(user);
    }

}