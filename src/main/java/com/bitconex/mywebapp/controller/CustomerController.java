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
    private UserService service;

    @GetMapping("/customers")
    public String showCustomerList(Model model) {
        List<User> listCustomers = service.listAll();
        model.addAttribute("listCustomers", listCustomers);

        return "customers";
    }

    @GetMapping("/customers/new")
    public String showNewForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("pageTitle", "Add New Customer");
        return "customer_form";
    }

    @PostMapping("/customers/save")
    public String saveCustomer(Customer customer, RedirectAttributes ra) {
        service.save(customer);
        ra.addFlashAttribute("message", "The customer has been saved successfully.");
        return "redirect:/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            User customer = service.get(id);
            model.addAttribute("customer", customer);
            model.addAttribute("pageTitle", "Edit Customer (ID: " + id + ")");

            return "customer_form";
        } catch (Exception e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/customers";
        }
    }

  /*  @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The customer ID " + id + " has been deleted.");
        } catch (Exception e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/customers";
    }*/
}

