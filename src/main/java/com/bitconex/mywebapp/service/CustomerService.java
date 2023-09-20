package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired private CustomerRepository repo;

    public List<Customer> listAll() {
        return (List<Customer>) repo.findAll();
    }

    public void save(Customer user) {
        repo.save(user);
    }

    public Customer get(Long id) throws Exception {
        Optional<Customer> existingCustomer = repo.findById(id);
        if (existingCustomer.isPresent()) {
            return existingCustomer.get();
        }
        throw new Exception("Could not find any users with ID " + id);
    }

    public void delete(Long id) throws Exception {
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new Exception("Could not find any users with ID " + id);
        }
        repo.deleteById(id);
    }
}