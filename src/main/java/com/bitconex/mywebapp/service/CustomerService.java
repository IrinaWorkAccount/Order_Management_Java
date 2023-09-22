package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired private CustomerRepository cr;

    public List<Customer> listAll() {
        return (List<Customer>) cr.findAll();
    }

    public void save(Customer customer) {
        cr.save(customer);
    }

    public Customer get(Long id) throws Exception {
        Optional<Customer> existingCustomer = cr.findById(id);
        if (existingCustomer.isPresent()) {
            return existingCustomer.get();
        }
        throw new Exception("Could not find any customers with ID " + id);
    }

    public void delete(Long id) throws Exception {
        Long count = cr.countById(id);
        if (count == null || count == 0) {
            throw new Exception("Could not find any customers with ID " + id);
        }
        cr.deleteById(id);
    }
}