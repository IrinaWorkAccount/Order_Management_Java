package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.Product;
import com.bitconex.mywebapp.model.User;
import com.bitconex.mywebapp.service.CustomerService;
import com.bitconex.mywebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
       /* List<User> allCus = UserService.listAll();
        System.out.println("Number of persisted users: " + allCus.size());*/
        System.out.println(toString());
    }
}
