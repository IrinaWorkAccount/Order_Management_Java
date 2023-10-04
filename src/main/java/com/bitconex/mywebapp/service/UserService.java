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
    UserRepository ur;

    public List<User> listAll() {
        return ur.findAll();
    }

    public void save(User user) {
        ur.save(user);
    }

    public User get(Long id) {
        Optional<User> existingUser = ur.findById(id);
        if (existingUser.isPresent()) {
            return existingUser.get();
        }
        throw new IllegalArgumentException("Could not find any users with ID " + id );
    }

   public void delete(String loginName) {
        Optional<User> existingUser = ur.findByUserLoginName(loginName);
        if (existingUser.isPresent()) {
            ur.delete(existingUser.get());
        } else {
            throw new IllegalArgumentException("User with ID " + loginName  + " not found.");
        }
    }

    //help methods
    /*public Optional<User> findByLoginName(String loginName) {
        return ur.findByUserLoginName(loginName);
    }*/
}
