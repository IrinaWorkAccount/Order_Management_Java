package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.*;
import com.bitconex.mywebapp.security.Role;
import com.bitconex.mywebapp.service.OrderService;
import com.bitconex.mywebapp.service.ProductService;
import com.bitconex.mywebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EntityScan("com.bitconex.mywebapp")
@EnableJpaRepositories("com.bitconex.mywebapp.repository")
public class MyWebAppApplication implements CommandLineRunner {
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(MyWebAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Hallo User");


        //Insert the required number of new entries (of type 'customer') into the User table. The loop increments each individual Customer by 1.


        for (int i = 0; i <= 1; i++) {
            Customer customer = new Customer();
            customer.setUserEmail("userEmail_" + i);
            customer.setUserLoginName("userLoginName_" + i);
            customer.setUserPassword("userPassword_" + i);
            customer.setCustomerName("Endera_" + i);
            customer.setCustomerSurname("Hifhra_" + i);
            customer.addRole(Role.CUSTOMER);
            LocalDate birthDate = LocalDate.of(1990, 9, 14);
            customer.setCustomerBirthDate(Date.valueOf(birthDate).toLocalDate());

            CustomerAddress address = new CustomerAddress();
            address.setCity("Musterstadt");
            address.setCountry("Musterland");
            address.setStreet("Feuer Str. 21");
            address.setZipCode("81546");

            customer.setCustomerAddress(address);
            userService.save(customer);
        }


        //Output all new entries from the list to the command line.

        List<User> allCus = userService.listAll();
        System.out.println("Number of persisted customers: " + allCus.size());
        for (User customers : allCus) {
            System.out.println(customers.toString());
            System.out.println("Customer ID: " + customers.getId());
            System.out.println("Customer Email: " + customers.getUserEmail());
            System.out.println("Customer Login: " + customers.getUserLoginName());
            System.out.println("Customers Role: " + customers.getRole());
            System.out.println();
        }


        //Insert the required number of new entries (of type 'admin') into the User table. The loop increments each individual Admin by 1.


        for (int i = 0; i <= 1; i++) {
            Admin admin = new Admin();
            admin.setUserEmail("userEmail_" + i);
            admin.setUserLoginName("userLoginName_" + i);
            admin.setUserPassword("userPassword_" + i);
            admin.addRole(Role.ADMIN);
            userService.save(admin);
        }


        //Output all new entries from the list to the command line.

        List<User> allAdms = userService.listAll();
        System.out.println("Number of persisted admins: " + allAdms.size());
        for (User admins : allAdms) {
            System.out.println(admins.toString());
            System.out.println("Admin ID: " + admins.getId());
            System.out.println("Admin Email: " + admins.getUserEmail());
            System.out.println("Admin Login: " + admins.getUserLoginName());
            System.out.println("Users Role: " + admins.getRole());
            System.out.println();
        }

        // Insert the required number of new entries (of type 'product') into the Product table. The loop increments each individual Product by 1.
        for (int i = 0; i <= 1; i++) {
            Product product = new Product();
            product.setProductName("productName_" + i);
            product.setProductSalePrice((1290.78) + i);
            Calendar today = Calendar.getInstance();
            product.setProductAvailableFrom(today.getTime());
            Calendar futureDate = Calendar.getInstance();
            futureDate.add(Calendar.YEAR, 1);
            product.setProductAvailableUntil((futureDate.getTime()));
            Random rn = new Random();
            int quantityRn = rn.nextInt(10) + 1;
            product.setProductQuantity(quantityRn);
            productService.save(product);
        }

        //Output all new entries from the list to the command line.

        List<Product> allPrds = productService.listAll();
        System.out.println("Number of persisted products: " + allPrds.size());
        for (Product products : allPrds) {
            System.out.println(products.toString());
            System.out.println("Product ID: " + products.getId());
            System.out.println("Product Name: " + products.getProductName());
            System.out.println("Product Price: " + products.getProductSalePrice());
            System.out.println("Product Available From: " + products.getProductAvailableFrom());
            System.out.println("Product Available Until: " + products.getProductAvailableUntil());
            System.out.println("Product Quantity: " + products.getProductQuantity());
            System.out.println();
        }

        // Insert the required number of new entries (of type 'order') into the Order table. The loop increments each individual Product by 1.
        Product product1 = new Product();
        product1.setProductName("productName_0");
        product1.setProductSalePrice(1290.78);
        Calendar today = Calendar.getInstance();
        product1.setProductAvailableFrom(today.getTime());
        Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.YEAR, 1);
        product1.setProductAvailableUntil(futureDate.getTime());
        Random rn = new Random();
        int quantityRn = rn.nextInt(10) + 1;
        product1.setProductQuantity(quantityRn);
        productService.save(product1);


        Customer customer = new Customer();
        customer.setId(2L);
        Order order1 = orderService.create(customer, 1, product1, "Pending");
        Order order2 = orderService.create(customer, 3, product1, "Pending");
        //orderService.delete(5L);

      /*  List<Order> allOrds = orderService.listAll();
        System.out.println("Number of persisted orders: " + allOrds.size());

        for (Order order : allOrds) {
            System.out.println("Order ID: " + order.getId());
            System.out.println("Order Status: " + order.getStatus());
            System.out.println("Order Quantity: " + order.getQuantity());

            // Access and print the associated product
            Product product = order.getProduct();
            System.out.println("Order contains Product: ");
            System.out.println("   Product Name: " + product.getProductName());
            System.out.println("   Sale Price: " + product.getProductSalePrice());
            System.out.println("   Available From: " + product.getProductAvailableFrom());
            System.out.println("   Available Until: " + product.getProductAvailableUntil());
            System.out.println("   Product Quantity: " + product.getProductQuantity());

            // Access and print the associated customer (user)
            System.out.println("Order belongs to the Customer: ");
            System.out.println("   Customer ID: " + customer.getId());

            System.out.println(); // Add a blank line to separate orders*/
        }
        }





