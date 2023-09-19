package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.CustomerAddress;
import com.bitconex.mywebapp.model.User;
import com.bitconex.mywebapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@EntityScan("com.bitconex.mywebapp")
@EnableJpaRepositories("com.bitconex.mywebapp.repository")
public class MyWebAppApplication implements CommandLineRunner {

    @Autowired
    CustomerService UserService;

    public static void main(String[] args) {
        SpringApplication.run(MyWebAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hallo User");
        List<User> allCus = UserService.getAllUsers();
        System.out.println("Number of persisted users: " + allCus.size());
        System.out.println(toString());
        CustomerAddress address = new CustomerAddress();
        address.setStreet("Musterstraße 123");
        address.setZipCode("12345");
        address.setCity("Musterstadt");
        address.setCountry("Musterland");

        Customer customer = new Customer("Benutzername", "benutzer@example.com", "Passwort", "Vorname", "Nachname", LocalDate.of(2000, 1, 1), address);

    }
}
