package com.bitconex.mywebapp.controller;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.User;
import com.bitconex.mywebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * A class that implement customer management functions, such as an adding, listing and deleting customers.
 */
@Controller
public class CustomerController {
    @Autowired
    private UserService us;

    @GetMapping("/customers")
    public String showCustomerList(Model model) {
        List<User> listCustomers = us.listAll();
        model.addAttribute("listCustomers", listCustomers);

        return "customers";
    }

}

