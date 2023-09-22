package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.User;
import com.bitconex.mywebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private static UserRepository ur;

    public static List<User> getAllUsers() {
        return ur.findAll();
    }

    public void save(User user) {
        ur.save(user);
    }

    public User getUser(Long id) throws Exception {
        Optional<User> existingUser = ur.findById(id);
        if (existingUser.isPresent()) {
            return existingUser.get();
        }
        throw new IllegalArgumentException("User with ID " + id + " not found.");
    }

/*    public void deleteUser(String loginName) {
        Optional<User> existingUser = ur.findByLoginName(loginName);
        if (existingUser.isPresent()) {
            ur.delete(existingUser.get());
        } else {
            throw new IllegalArgumentException("User with ID " + loginName  + " not found.");
        }
    }

    //help methods
    public Optional<User> findByLoginName(String loginName) {
        return ur.findByLoginName(loginName);
    }*/
}
