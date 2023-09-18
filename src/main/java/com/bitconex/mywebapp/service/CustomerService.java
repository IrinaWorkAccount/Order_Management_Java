package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.User;
import com.bitconex.mywebapp.repository.CustomerRepository;
import com.bitconex.mywebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService extends UserService{
    @Autowired private static UserRepository repo;

    public static List<User> listAll() {
        return repo.findAll();
    }

    public void save(Customer user) {
        repo.save(user);
    }

    public User getCustomer(Long id) throws Exception {
        Optional<User> existingCustomer = repo.findById(id);
        if (existingCustomer.isPresent()) {
            return existingCustomer.get();
        }
        throw new IllegalArgumentException("Product with ID " + id + " not found.");
    }

    public void deleteCustomer(Long id) {
        Optional<User> existingCustomer = repo.findById(id);
        if (existingCustomer.isPresent()) {
            repo.deleteById(id);
        } else {
            throw new IllegalArgumentException("Product with ID " + id + " not found.");
        }
    }
}