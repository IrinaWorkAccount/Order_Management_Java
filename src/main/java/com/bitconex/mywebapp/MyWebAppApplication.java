package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Admin;
import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.security.Role;
import com.bitconex.mywebapp.service.AdminService;
import com.bitconex.mywebapp.service.CustomerService;
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
public class MyWebAppApplication implements CommandLineRunner { //implements CommandLineRunner

    @Autowired
    CustomerService customerService;
    @Autowired
    AdminService adminService;

    public static void main(String[] args) {
        SpringApplication.run(MyWebAppApplication.class, args);
    }

 /*   @GetMapping("")
    public List<Admin> hallo(){
        return List.of(
                new Admin(
                        "userEmail",
                        "userLoginName",
                        "userPassword"

                )
        );
    }*/

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Hallo User");


        /**
         * Insert the required number of  new entries (of type 'customer') into the User table. The loop increments each individual Admin by 1.
         */

        for(int i=0; i<=5; i++) {
            Customer customer = new Customer();
            customer.setUserEmail("userEmail_"+i);
            customer.setUserLoginName("userLoginName_"+i);
            customer.setUserPassword("userPassword_"+i);
            customer.addRole(Role.CUSTOMER);
            customerService.save(customer);
        }

/**
 * Output all new entries from the list to the command line.
 */

        List<Customer> allCus = customerService.listAll();
        System.out.println("Number of persisted customers: " + allCus.size());
        for(Customer customers: allCus){
            System.out.println(customers.toString());
            System.out.println("Customer ID: " + customers.getId());
            System.out.println("Customer Email: " + customers.getUserEmail());
            System.out.println("Customer Login: " + customers.getUserLoginName());
            System.out.println("Customers Role: " + customers.getRole());
            System.out.println();
        }


/**
 * Insert the required number of  new entries (of type 'admin') into the User table. The loop increments each individual Admin by 1.
 */

for(int i=0; i<=5; i++) {
    Admin admin = new Admin();
    admin.setUserEmail("userEmail_"+i);
    admin.setUserLoginName("userLoginName_"+i);
    admin.setUserPassword("userPassword_"+i);
    admin.addRole(Role.ADMIN);
    adminService.save(admin);
}

/**
 * Output all new entries from the list to the command line.
 */

        List<Admin> allAdms = adminService.listAll();
        System.out.println("Number of persisted admins: " + allAdms.size());
        for(Admin admins: allAdms){
            System.out.println(admins.toString());
            System.out.println("Admin ID: " + admins.getId());
            System.out.println("Admin Email: " + admins.getUserEmail());
            System.out.println("Admin Login: " + admins.getUserLoginName());
            System.out.println("Users Role: " + admins.getRole());
            System.out.println();
        }
        }
}


