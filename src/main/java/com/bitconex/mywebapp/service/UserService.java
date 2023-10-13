package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.User;
import com.bitconex.mywebapp.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class UserService {

    // ==============
    // PRIVATE FIELDS
    // ==============

    @Autowired
    private UserRepository ur;

    // ==============
    // PUBLIC METHODS
    // ==============

    /**
     * Retrieves all users from the user database without their passwords.
     */
    public List<User> listAll() {
        List <User> users = ur.findAll();
        users.forEach(b->b.setUserPassword(null)); //return all data except password
        return users;
    }

    /**
     * Converts all users into JSON format.
     */
    @Transactional
    public String listAllJSOn()throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> userList = ur.findAll();
        return objectMapper.writeValueAsString(userList);
    }

    /**
     *  Saves a user in the database.
     */
    public void save(User user) {
        ur.save(user);
    }

    /**
     * Searches for a user by their ID and returns them.
     */
    public User get(Long id) {
        Optional<User> existingUser = ur.findById(id);
        if (existingUser.isPresent()) {
            return existingUser.get();
        }
        throw new IllegalArgumentException("Could not find any users with ID " + id );
    }

    /**
     * Deletes a user by their username from the database.
     */
   public void delete(String userLogin) {
        Optional<User> existingUser = ur.findByUserLogin(userLogin);
        if (existingUser.isPresent()) {
            ur.delete(existingUser.get());
        } else {
            throw new IllegalArgumentException("User with ID " + userLogin  + " not found.");
        }
    }

    public User authenticateUser(String enteredLogin, String enteredPassword) {
       Optional<User>optionalUser = ur.findByUserLogin(enteredLogin);

       if(optionalUser.isPresent()){
           User user = optionalUser.get();

           if(user.getUserPassword().equals(enteredPassword)){
               return user;
           }
       }
       return null;
    }


   public Date scanToDate(String input) throws ParseException {
        try (Scanner scanner = new Scanner(input)) {
            String dateString = scanner.next();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(dateString);
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }


    //help methods
    /*public Optional<User> findByLoginName(String loginName) {
        return ur.findByUserLogin(userLogin);
    }*/
}
