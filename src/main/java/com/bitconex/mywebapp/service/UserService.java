package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.User;
import com.bitconex.mywebapp.repository.CustomerRepository;
import com.bitconex.mywebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserService {

    @Autowired
    private static UserRepository repo;

    public static List<User> listAll() {
        return repo.findAll();
    }

    public void save(User user) {
        repo.save(user);
    }

    public User getUser(Long id) throws Exception {
        Optional<User> existingUser = repo.findById(id);
        if (existingUser.isPresent()) {
            return existingUser.get();
        }
        throw new IllegalArgumentException("User with ID " + id + " not found.");
    }

    public void deleteUser(Long id) {
        Optional<User> existingUser = repo.findById(id);
        if (existingUser.isPresent()) {
            repo.deleteById(id);
        } else {
            throw new IllegalArgumentException("User with ID " + id + " not found.");
        }
    }

}
