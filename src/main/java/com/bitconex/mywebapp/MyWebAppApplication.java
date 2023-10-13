package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.*;
import com.bitconex.mywebapp.repository.CustomerRepository;
import com.bitconex.mywebapp.repository.OrderRepository;
import com.bitconex.mywebapp.repository.ProductRepository;
import com.bitconex.mywebapp.repository.UserRepository;
import com.bitconex.mywebapp.security.Role;
import com.bitconex.mywebapp.service.OrderService;
import com.bitconex.mywebapp.service.ProductService;
import com.bitconex.mywebapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.expression.ParseException;

import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
@EntityScan("com.bitconex.mywebapp")
@EnableJpaRepositories("com.bitconex.mywebapp.repository")
public class MyWebAppApplication implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(SpringBootApplication.class);

    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    public static void main(String[] args) {
        LOG.info("\n 1. STARTING : Spring boot application starting");//First logger message
        SpringApplication.run(MyWebAppApplication.class, args);
        LOG.info("\n 3. STOPPED : Spring boot application stopped");
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Hello User"); // Greeting message
        LOG.info("\n 2. EXECUTING : command line runner"); // Logging execution
        System.out.println(userService.listAllJSOn());

        Scanner scanner = new Scanner(System.in);

        boolean isRunning = true;
        Customer customer = null;
        boolean isAdmin = false;

        System.out.print("Enter your login name: ");
        String enteredLogin = scanner.nextLine();

        System.out.print("Enter your password: ");
        String enteredPassword = scanner.nextLine();

        User user = userService.authenticateUser(enteredLogin, enteredPassword);
        if (user != null) {
            if (user.getRole() == Role.ADMIN) {

                System.out.println("Welcome, Admin!");
                while (isRunning) {
                    System.out.println("Administrator Panel");
                    System.out.println("1. Create a New User");
                    System.out.println("2. List of All Users");
                    System.out.println("3. Delete a User");
                    System.out.println("4. Product Catalog");
                    System.out.println("5. List of All Orders");
                    System.out.println("6. Exit program");
                    System.out.print("Please enter the desired option: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1: //create a new user

                            while (true) {
                                try {
                                    System.out.print("Select the role of the new user (1 for Admin, 2 for Customer): ");
                                    int userRoleChoice = scanner.nextInt();
                                    scanner.nextLine();

                                    System.out.print("Enter the login name: ");
                                    String loginName = scanner.nextLine();

                                    System.out.print("Enter the password: ");
                                    String password = scanner.nextLine();

                                    System.out.print("Enter the email address: ");
                                    String email = scanner.nextLine();
                                    if (userRoleChoice == 1) {

                                        User admin = new Admin(loginName, email, password);
                                        userService.save(admin);

                                    } else if (userRoleChoice == 2) {
                                        // Create a Customer
                                        System.out.print("Enter the name: ");
                                        String name = scanner.nextLine();

                                        System.out.print("Enter the surname: ");
                                        String surname = scanner.nextLine();
                                        System.out.print("Enter the birth date (YYYY-MM-DD): ");
                                        String birthDate = scanner.nextLine();
                                        Date birthDateNew = userService.scanToDate(birthDate);

                                        System.out.print("Enter the street and house number: ");
                                        String street = scanner.nextLine();

                                        System.out.print("Enter the zip code: ");
                                        String zipCode = scanner.nextLine();

                                        System.out.print("Enter the city: ");
                                        String city = scanner.nextLine();

                                        System.out.print("Enter the country: ");
                                        String country = scanner.nextLine();

                                        User customerToCreate = new Customer(loginName, email, password, name, surname, birthDateNew, new CustomerAddress(street, zipCode, city, country));
                                        userService.save(customerToCreate);

                                    }

                                    System.out.println("Customer created successfully.");

                                    break;


                                } catch (ParseException e) {
                                    System.out.println("Invalid input. Please enter again.");
                                } catch (Exception e) {
                                    System.out.println("An error occurred: " + e.getMessage());
                                }

                            }

                        case 2: // List all users
                            String usersJson = userService.listAllJSOn();
                            System.out.println("List of all users in JSON format:");
                            System.out.println(usersJson);
                            break;

                        case 3: // Delete a user
                            System.out.print("Enter the login name of the user to delete: ");
                            String userLoginToDelete = scanner.nextLine();
                            userService.delete(userLoginToDelete);
                            System.out.println("User deleted successfully.");
                            break;

                        case 4: // Product Catalog
                            while (true) {
                                System.out.println("Product Catalog");
                                System.out.println("1. Add a new product");
                                System.out.println("2. List all products");
                                System.out.println("3. Delete a product");
                                System.out.println("4. Back to Administration");
                                System.out.print("Please enter the desired option: ");

                                int productCatalogChoice = scanner.nextInt();
                                scanner.nextLine();

                                switch (productCatalogChoice) {
                                    case 1: // Add a new product
                                        System.out.print("Enter the product name: ");
                                        String productName = scanner.nextLine();

                                        System.out.print("Enter the sale price of the product: ");
                                        double salePrice = scanner.nextDouble();
                                        scanner.nextLine();

                                        System.out.print("Enter the available from date (YYYY-MM-DD): ");
                                        String availableFromDate = scanner.nextLine();
                                        Date availableFromDateNew = userService.scanToDate(availableFromDate);


                                        System.out.print("Enter the available until date (YYYY-MM-DD): ");
                                        String availableUntilDate = scanner.nextLine();
                                        Date availableUntilDateNew = userService.scanToDate(availableUntilDate);

                                        System.out.print("Enter the quantity of product instances available for sale: ");
                                        int quantity = scanner.nextInt();
                                        scanner.nextLine();

                                        productService.add(new Product(productName, salePrice, availableFromDateNew, availableUntilDateNew, quantity));
                                        System.out.println("Product added successfully.");
                                        break;

                                    case 2: // List all products
                                        String productsJson = productService.listAllProductsInJson();
                                        System.out.println("List of all products in the catalog in JSON format:");
                                        System.out.println(productsJson);
                                        break;

                                    case 3: // Delete a product
                                        System.out.print("Enter the ID of the product to delete: ");
                                        long productIdToDelete = scanner.nextLong();
                                        productService.delete(productIdToDelete);
                                        System.out.println("Product deleted successfully.");
                                        break;

                                    case 4: // Back to Administration
                                        break;

                                    default:
                                        System.out.println("Invalid option. Please select a valid option.");
                                }

                                if (productCatalogChoice == 4) {
                                    break; // Return to the main Administration menu
                                }
                            }
                            break;

                        case 5: // List all orders
                            String ordersJson = orderService.convertOrdersToJson();
                            System.out.println("List of all orders in JSON format:");
                            System.out.println(ordersJson);
                            break;
                    }
                }
            } else if (user.getRole() == Role.CUSTOMER) {

                System.out.println("Welcome, Customer!");

                while (isRunning) {

                    System.out.println("Customer Panel");
                    System.out.println("1. Place a new order");
                    System.out.println("2. View orders");
                    System.out.println("3. Exit program");
                    System.out.print("Please enter the desired option: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            System.out.print("Enter the user ID: ");
                            long userId = scanner.nextLong();

                            Optional<User> customerOptional = userRepository.findById(userId);

                            if (customerOptional.isPresent()) {
                                User user1 = customerOptional.get();
                                if (customer.getRole() == Role.CUSTOMER) {
                                    System.out.print("Enter the product ID you want to order: ");
                                    long productId = scanner.nextLong();
                                    Optional<Product> productOptional = productRepository.findById(productId);

                                    if (productOptional.isPresent()) {

                                        Product product = productOptional.get();
                                        int quantity = 0;

                                        while (quantity <= 0) {
                                            System.out.print("Enter the desired quantity you want to order: ");
                                            quantity = scanner.nextInt();

                                            if (quantity <= 0) {
                                                System.out.println("Invalid quantity. Quantity must be greater than 0.");
                                            } else if (quantity > product.getProductQuantity()) {
                                                System.out.println("Not enough items in stock. Please specify a different quantity.");
                                                quantity = 0; // Reset the quantity to 0 to repeat the loop
                                            }
                                        }

                                        Order newOrder = orderService.create(customer, quantity, product, "In Progress");

                                        System.out.println("Order created successfully.");

                                    } else {
                                        System.out.println("Product with ID " + productId + " not found.");
                                    }
                                } else {
                                    System.out.println("User with ID " + userId + " does not have the required 'CUSTOMER' role.");
                                }
                            } else {
                                System.out.println("User with ID " + userId + " not found.");
                            }
                            break;
                        case 2:
                            while (true) {


                                if (customer != null) {
                                    Optional<Order> customerOrders = orderRepository.findByCustomer(customer);
                                    String ordersJson = orderService.convertOrdersToJson(customerOrders);
                                    System.out.println("List of all orders in JSON format:");
                                    System.out.println(ordersJson);


                                } else {
                                    System.out.println("No customer selected. Please enter a customer ID first.");
                                    long userId1 = scanner.nextLong();
                                }

                                break;
                            }

                        case 3:
                            System.out.println("Program is exiting.");
                            isRunning = false;
                            break;
                        default:
                            System.out.println("Invalid option. Please select a valid option.");
                    }
                }
            }
        }
        scanner.close();


        //Insert the required number of new entries (of type 'customer') into the User table. The loop increments each individual Customer by 1.


/*        for (int i = 0; i <= 1; i++) {
            Customer customer = new Customer();
            customer.setUserEmail("userEmail_" + i);
            customer.setUserLogin("userLoginName_" + i);
            customer.setUserPassword("userPassword_" + i);
            customer.setCustomerName("Endera_" + i);
            customer.setCustomerSurname("Hifhra_" + i);
            customer.setRole(Role.CUSTOMER);
            LocalDate birthDate = new Date(1990-9-14);
            customer.setCustomerBirthDate(birthDate);

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
            System.out.println("Customer Login: " + customers.getUserLogin());
            System.out.println("Customers Role: " + customers.getRole());
            System.out.println();
        }


        //Insert the required number of new entries (of type 'admin') into the User table. The loop increments each individual Admin by 1.


        for (int i = 0; i <= 1; i++) {
            Admin admin = new Admin();
            admin.setUserEmail("userEmail_" + i);
            admin.setUserLogin("userLoginName_" + i);
            admin.setUserPassword("userPassword_" + i);
            admin.setRole(Role.ADMIN);
            userService.save(admin);
        }


        //Output all new entries from the list to the command line.

        List<User> allAdms = userService.listAll();
        System.out.println("Number of persisted admins: " + allAdms.size());
        for (User admins : allAdms) {
            System.out.println(admins.toString());
            System.out.println("Admin ID: " + admins.getId());
            System.out.println("Admin Email: " + admins.getUserEmail());
            System.out.println("Admin Login: " + admins.getUserLogin());
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


        Customer customer2 = new Customer();
        Customer customer3 = new Customer();
        customer2.setId(2L);
        customer3.setId(3L);
        Order order1 = orderService.create(customer2, product1.getProductQuantity(), product1, "Pending");
        Order order2 = orderService.create(customer3, product1.getProductQuantity(), product1, "Pending");
        //orderService.delete(5L);

        List<Order> allOrds = orderService.listAll();
        allOrds.add(7, order1);
        allOrds.add(2, order2);

        orderService.save(order1);
        orderService.save(order2);

        String orderList = allOrds.toString();

        System.out.println("Number of persisted orders: " + allOrds.size());
        System.out.println("Liste aller Bestellungen: " + orderList);

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
            System.out.println("   Customer ID: " + order.getUser().getId());

            System.out.println(); // Add a blank line to separate orders
        }*/


    }
}







