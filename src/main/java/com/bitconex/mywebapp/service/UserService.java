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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * The `UserService` class provides methods for managing user-related functions, such as retrieving user details, authentication, and managing user data. It also offers functionality to retrieve a list of all users in JSON format.
 */
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
     * Retrieves a list of all users from the user database without their passwords.
     *
     * @return A list of users with password information removed.
     */
    public List<User> listAll() {
        List <User> users = ur.findAll();
        users.forEach(b->b.setUserPassword(null)); //return all data except password
        return users;
    }

    /**
     * Converts all users into JSON format.
     *
     * @return A JSON representation of all users in the database.
     * @throws JsonProcessingException if there is an issue converting users to JSON.
     */
    @Transactional
    public String listAllJSOn()throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> userList = ur.findAll();
        return objectMapper.writeValueAsString(userList);
    }

    /**
     * Saves a user in the database.
     *
     * @param user The user to be saved.
     * @return The saved user.
     */
    public void save(User user) {
        ur.save(user);
    }

    /**
     * Searches for a user by their ID and returns them.
     *
     * @param id The ID of the user to search for.
     * @return The user with the specified ID.
     * @throws IllegalArgumentException if no user is found with the given ID.
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
     *
     * @param userLogin The username of the user to be deleted.
     * @throws IllegalArgumentException if no user is found with the given username.
     */
   public void delete(String userLogin) {
        Optional<User> existingUser = ur.findByUserLogin(userLogin);
        if (existingUser.isPresent()) {
            ur.delete(existingUser.get());
        } else {
            throw new IllegalArgumentException("User with ID " + userLogin  + " not found.");
        }
    }

    /**
     * Authenticates a user based on entered login and password.
     *
     * @param enteredLogin The entered login/username.
     * @param enteredPassword The entered password.
     * @return The authenticated user if login and password match; otherwise, returns null.
     */
    public User authenticateUser(String enteredLogin, String enteredPassword) {
        System.out.println("This is the entered login: " + enteredLogin);
        System.out.println("This is the entered password: " + enteredPassword);
        Optional<User> optionalUser = ur.findByUserLogin(enteredLogin);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getUserPassword().equals(enteredPassword)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Edits a user's information.
     *
     * @param userId The ID of the user to be edited.
     * @param newUserName The new username for the user.
     * @param newUserEmail The new email for the user.
     * @param newUserPassword The new password for the user.
     * @return The edited user if found and updated; otherwise, returns null.
     */
    public User editUser(Long userId, String newUserName, String newUserEmail, String newUserPassword) {
        User user = ur.findById(userId).orElse(null);
        if (user != null) {
            user.setUserLogin(newUserName);
            user.setUserEmail(newUserEmail);
            user.setUserPassword(newUserPassword);
            return ur.save(user);
        }
        return null;
    }

    /**
     * Parses a date string into a `Date` object.
     *
     * @param input The date string to be parsed.
     * @return A `Date` object representing the parsed date.
     * @throws ParseException if there is an issue parsing the date string.
     */
    public Date scanToDate(String input) throws ParseException {
        try (Scanner scanner = new Scanner(input)) {
            String dateString = scanner.next();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(dateString);
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
