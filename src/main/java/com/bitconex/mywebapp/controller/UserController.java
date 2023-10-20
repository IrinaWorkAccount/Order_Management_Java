package com.bitconex.mywebapp.controller;


import com.bitconex.mywebapp.model.Admin;
import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.CustomerAddress;
import com.bitconex.mywebapp.model.User;
import com.bitconex.mywebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A class that implement endpoints for User such an adding, listing and deleting users.
 *
 * @autor Irina Barvenko
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    // Endpoint for creating a new admin
    @PostMapping("/create/admin")
    public String createAdmin(@RequestParam String userName, @RequestParam String userPassword, @RequestParam String userEmail) {
        Admin newUser = new Admin(userName, userPassword, userEmail);
        userService.save(newUser);
        return "The admin has been successfully saved.";
    }

    // Endpoint for creating a new customer
    @PostMapping("/create/customer")
    public String createCustomer(@RequestParam String userLoginName,
                                 @RequestParam String userPassword,
                                 @RequestParam String userEmail,
                                 @RequestParam String customerName,
                                 @RequestParam String customerSurname,
                                 @RequestParam String customerBirthDate,
                                 @RequestParam String customerAddressStreet,
                                 @RequestParam String customerAddressZipCode,
                                 @RequestParam String customerAddressCity,
                                 @RequestParam String customerAddressCountry) {
        CustomerAddress address = new CustomerAddress(customerAddressStreet, customerAddressZipCode, customerAddressCity, customerAddressCountry);
        Date birthDate = parseBirthDate(customerBirthDate);
        Customer newCustomer = new Customer(userLoginName, userEmail, userPassword, customerName, customerSurname, birthDate, address);
        userService.save(newCustomer);

        return "The customer has been successfully saved.";
    }

    private Date parseBirthDate(String customerBirthDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(customerBirthDate);
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Endpoint for retrieving all users
    @GetMapping("")
    public ResponseEntity<List<User>> listAllUsers() { //
        List<User> listUsers = userService.listAll();
        return ResponseEntity.ok(listUsers);
    }

    // Endpoint for deleting a user by ID
    @RequestMapping("/delete/{login}")
    public ResponseEntity<String> deleteUser(@PathVariable String login) {
        userService.delete(login);
        return ResponseEntity.ok("User " + login + " has been deleted.");
    }

    // Endpoint for retrieving an individual user by ID
    @RequestMapping("/show/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.get(userId);
    }

    // Endpoint for verifying and authenticating a user
    @PostMapping("/autenticate")
    public ResponseEntity<User> authenticateUser(@RequestParam String login, @RequestParam String password) {
        User authenticatedUser = userService.authenticateUser(login, password);
        if (authenticatedUser != null) {
            return ResponseEntity.ok(authenticatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Endpoint for edit a user
    @PostMapping("/edit/{userId}")
    public ResponseEntity<?> editUser(
            @PathVariable Long userId,
            @RequestParam String newUserName,
            @RequestParam String newUserEmail,
            @RequestParam String newUserPassword) {
        User editedUser = userService.editUser(userId, newUserName, newUserEmail, newUserPassword);
        if (editedUser != null) {
            System.out.println("The user has been successfully edited");
            return ResponseEntity.ok(editedUser);
        } else {
            String errorMessage = "Error: The user was not found or the editing failed.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);

        }
    }


}







