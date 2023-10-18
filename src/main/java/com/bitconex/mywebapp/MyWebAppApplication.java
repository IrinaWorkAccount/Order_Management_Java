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
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
@EntityScan("com.bitconex.mywebapp")
@EnableJpaRepositories("com.bitconex.mywebapp.repository")
public class MyWebAppApplication implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(MyWebAppApplication.class);

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;

    public static void main(String[] args) {
        LOG.info("\n 1. STARTING : Spring boot application starting");//First logger message
        SpringApplication.run(MyWebAppApplication.class, args);
        LOG.info("\n 3. STOPPED : Spring boot application stopped");
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello User"); // Greeting message
        LOG.info("\n 2. EXECUTING : command line runner"); // Logging execution
        Scanner scanner = new Scanner(System.in);

        boolean isRunning = true;
        Customer customer = null;
        int quantity = 0;
        Product product = null;
        User user = null;
        while (isRunning) {
            System.out.print("Enter your login name: ");
            String enteredLogin = scanner.nextLine();

            System.out.print("Enter your password: ");
            String enteredPassword = scanner.nextLine();
            user = userService.authenticateUser(enteredLogin, enteredPassword);

            if (user != null) {
                break;
            } else {
                System.out.println("User or password is incorrect. Please try again.");
            }
        }

        if (user.getRole() == Role.ADMIN) {
            adminPanel(user, scanner);
        } else if (user.getRole() == Role.CUSTOMER) {
            customerPanel(user, scanner);
        }
    }

    // Admin Panel
    private void adminPanel(User user, Scanner scanner) throws JsonProcessingException {
        System.out.println("Welcome, Admin!");

        while (true) {
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
                case 1: // Create a new user
                    createUser(userService, scanner);
                    break;
                case 2: // List all users
                    listAllUsers(userService);
                    break;
                case 3: // Delete a user
                    deleteUser(userService, scanner);
                    break;
                case 4: // Product Catalog
                    productCatalog(scanner);
                    break;
                case 5: // List all orders
                    listAllOrders(orderService);
                    break;
                case 6: // Exit program
                    return;
                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }

    private void createUser(UserService userService, Scanner scanner) {
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
            // Create an Admin user
            User admin = new Admin(loginName, email, password);
            userService.save(admin);
            System.out.println("Admin created successfully.");
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
            System.out.println("Customer created successfully.");
        }
    }

    private void listAllUsers(UserService userService) throws JsonProcessingException {
        String usersJson = userService.listAllJSOn();
        System.out.println("List of all users in JSON format:");
        System.out.println(usersJson);
    }

    private void deleteUser(UserService userService, Scanner scanner) {
        System.out.print("Enter the login name of the user to delete: ");
        String userLoginToDelete = scanner.nextLine();
        userService.delete(userLoginToDelete);
        System.out.println("User deleted successfully.");
    }

    private void productCatalog(Scanner scanner) throws JsonProcessingException {
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
                    createProduct(productService, scanner);
                    break;
                case 2: // List all products
                    listAllProducts(productService);
                    break;
                case 3: // Delete a product
                    deleteProduct(productService, scanner);
                    break;
                case 4: // Back to Administration
                    return;
                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }

    // Create a new product
    private void createProduct(ProductService productService, Scanner scanner) {
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
    }

    // List all products
    private void listAllProducts(ProductService productService) throws JsonProcessingException {
        String productsJson = productService.listAllProductsInJson();
        System.out.println("List of all products in the catalog in JSON format:");
        System.out.println(productsJson);
    }

    // Delete a product
    private void deleteProduct(ProductService productService, Scanner scanner) {
        System.out.print("Enter the ID of the product to delete: ");
        long productIdToDelete = scanner.nextLong();
        scanner.nextLine();
        productService.delete(productIdToDelete);
        System.out.println("Product deleted successfully.");
    }

    // List all orders
    private void listAllOrders(OrderService orderService) throws JsonProcessingException {
        String ordersJson = orderService.convertOrdersToJson();
        System.out.println("List of all orders in JSON format:");
        System.out.println(ordersJson);
    }

    // Customer Panel
    private void customerPanel(User user, Scanner scanner) throws JsonProcessingException {
        System.out.println("Welcome, Customer!");

        while (true) {
            System.out.println("Customer Panel");
            System.out.println("1. Place a New Order");
            System.out.println("2. View Orders");
            System.out.println("3. Exit program");
            System.out.print("Please enter the desired option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: // Place a new order
                    placeOrder(userService, productService, orderService, scanner, user);
                    break;
                case 2: // View orders
                    viewOrders(orderService, user);
                    break;
                case 3: // Exit program
                    return;
                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }


    private void placeOrder(UserService userService, ProductService productService, OrderService orderService, Scanner scanner, User user) {
        long userId = user.getId();
        Optional<Customer> customerOptional = customerRepository.findById(userId);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            System.out.print("Enter the product ID you want to order: ");
            long productId = scanner.nextLong();
            scanner.nextLine();
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
            System.out.println("Customer with ID " + userId + " not found.");
        }
    }

    private void viewOrders(OrderService orderService, User user) throws JsonProcessingException {
        Long userId = user.getId();
        //Optional<Order> customerOrders = orderRepository.findById(userId);
        if (userId != null) {
            String ordersJson = orderService.getOrdersJsonForUser(userId);
            System.out.println("Your orders in JSON format:");
            System.out.println(ordersJson);
        }
    }
}


   /* @Override
    public void run(String... args) throws Exception {

        System.out.println("Hello User"); // Greeting message
        LOG.info("\n 2. EXECUTING : command line runner"); // Logging execution

        Scanner scanner = new Scanner(System.in);

        boolean isRunning = true;
        Customer customer = null;
        int quantity = 0;
        Product product = null;
        User user = null;
        while (isRunning) {
            System.out.print("Enter your login name: ");
            String enteredLogin = scanner.nextLine();

            System.out.print("Enter your password: ");
            String enteredPassword = scanner.nextLine();

            user = userService.authenticateUser(enteredLogin, enteredPassword);
            if (user != null) {
                break;
            } else {
                System.out.println("User or password is incorrect. Please try again.");
            }
        }
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

                                String loginName = null;
                                String password = null;
                                String email = null;
                                boolean validInput = false;
                                int userRoleChoice = 0;


                                try {

                                    try {
                                        System.out.print("Select the role of the new user (1 for Admin, 2 for Customer): ");
                                        userRoleChoice = scanner.nextInt();
                                        scanner.nextLine();
                                    } catch (Exception e) {
                                        System.out.println("Invalid input for the user role. Please enter again.");
                                        break;


                                    }
                                    try {
                                        System.out.print("Enter the login name: ");
                                        loginName = scanner.nextLine();
                                    } catch (Exception e) {
                                        System.out.println("Invalid input for the admin login name. Please enter again.");
                                        continue;
                                    }
                                    System.out.print("Enter the password: ");
                                    password = scanner.nextLine();

                                    System.out.print("Enter the email address: ");
                                    email = scanner.nextLine();
                                    if (userRoleChoice == 1) {

                                        User admin = new Admin(loginName, email, password);
                                        userService.save(admin);
                                        System.out.println("Admin created successfully.");


                                    } else if (userRoleChoice == 2) {
                                        // Create a Customer
                                        String name = null;
                                        String surname = null;
                                        Date birthDateNew = null;
                                        String street = null;
                                        String zipCode = null;
                                        String city = null;
                                        String country = null;
                                        try {
                                            System.out.print("Enter the name: ");
                                            name = scanner.nextLine();
                                        } catch (Exception e) {
                                            System.out.println("Invalid input. Please enter again.");
                                            continue;
                                        }
                                        try {
                                            System.out.print("Enter the surname: ");
                                            surname = scanner.nextLine();
                                        } catch (Exception e) {
                                            System.out.println("Invalid input. Please enter again.");
                                            continue;
                                        }
                                        try {
                                            System.out.print("Enter the birth date (YYYY-MM-DD): ");
                                            String birthDate = scanner.nextLine();
                                            birthDateNew = userService.scanToDate(birthDate);
                                        } catch (Exception e) {
                                            System.out.println("Invalid input. Please enter again.");
                                            continue;
                                        }
                                        try {
                                            System.out.print("Enter the street and house number: ");
                                            street = scanner.nextLine();
                                        } catch (Exception e) {
                                            System.out.println("Invalid input. Please enter again.");
                                            continue;
                                        }
                                        try {
                                            System.out.print("Enter the zip code: ");
                                            zipCode = scanner.nextLine();
                                        } catch (Exception e) {
                                            System.out.println("Invalid input. Please enter again.");
                                            continue;
                                        }
                                        try {
                                            System.out.print("Enter the city: ");
                                            city = scanner.nextLine();
                                        } catch (Exception e) {
                                            System.out.println("Invalid input. Please enter again.");
                                            continue;
                                        }
                                        try {
                                            System.out.print("Enter the country: ");
                                            country = scanner.nextLine();
                                        } catch (Exception e) {
                                            System.out.println("Invalid input. Please enter again.");
                                            continue;
                                        }
                                        User customerToCreate = new Customer(loginName, email, password, name, surname, birthDateNew, new CustomerAddress(street, zipCode, city, country));
                                        userService.save(customerToCreate);

                                        System.out.println("Customer created successfully.");

                                    }

                                    return;

                                } catch (ParseException e) {
                                    System.out.println("Invalid input. Please enter again.");
                                } catch (Exception e) {
                                    System.out.println("An error occurred: " + e.getMessage());
                                }
                                break;
                            }

                        case 2: // List all users
                            String usersJson = userService.listAllJSOn();
                            System.out.println("List of all users in JSON format:");
                            System.out.println(usersJson);
                            break;

                        case 3: // Delete a user
                            try {
                                System.out.print("Enter the login name of the user to delete: ");
                                String userLoginToDelete = scanner.nextLine();
                                userService.delete(userLoginToDelete);
                                System.out.println("User deleted successfully.");
                            } catch (Exception e) {
                                System.out.println("Invalid input. Please try again.");
                            }
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
                                        while (true) {
                                            String productName = null;
                                            double salePrice = 0.0;
                                            Date availableFromDateNew = null;
                                            Date availableUntilDateNew = null;


                                            try {
                                                System.out.print("Enter the product name: ");
                                                productName = scanner.nextLine();
                                            } catch (Exception e) {
                                                System.out.println("Invalid input. Please enter again.");
                                                continue;
                                            }


                                            try {
                                                System.out.print("Enter the sale price of the product: ");
                                                salePrice = scanner.nextDouble();
                                                scanner.nextLine();
                                            } catch (Exception e) {
                                                System.out.println("Invalid input. Please enter again.");
                                                continue;
                                            }

                                            try {
                                                System.out.print("Enter the available from date (YYYY-MM-DD): ");
                                                String availableFromDate = scanner.nextLine();
                                                availableFromDateNew = userService.scanToDate(availableFromDate);
                                            } catch (Exception e) {
                                                System.out.println("Invalid input. Please enter again.");
                                                continue;
                                            }


                                            try {
                                                System.out.print("Enter the available until date (YYYY-MM-DD): ");
                                                String availableUntilDate = scanner.nextLine();
                                                availableUntilDateNew = userService.scanToDate(availableUntilDate);
                                            } catch (Exception e) {
                                                System.out.println("Invalid input. Please enter again.");
                                                continue;
                                            }


                                            try {
                                                System.out.print("Enter the quantity of product instances available for sale: ");
                                                quantity = scanner.nextInt();
                                                scanner.nextLine();
                                            } catch (Exception e) {
                                                System.out.println("Invalid input. Please enter again.");
                                                continue;
                                            }

                                            productService.add(new Product(productName, salePrice, availableFromDateNew, availableUntilDateNew, quantity));
                                            System.out.println("Product added successfully.");

                                            break;
                                        }
                                        break;

                                    case 2: // List all products
                                        String productsJson = productService.listAllProductsInJson();
                                        System.out.println("List of all products in the catalog in JSON format:");
                                        System.out.println(productsJson);
                                        break;

                                    case 3: // Delete a product
                                        try {
                                            System.out.print("Enter the ID of the product to delete: ");
                                            long productIdToDelete = scanner.nextLong();
                                            productService.delete(productIdToDelete);
                                            System.out.println("Product deleted successfully.");
                                        } catch (Exception e) {
                                            System.out.println("Invalid input. Please enter again.");
                                        }
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
                            long userId = user.getId();
                            Optional<Customer> customerOptional = customerRepository.findById(userId);

                            if (customerOptional.isPresent()) {
                                customer = customerOptional.get();

                                System.out.print("Enter the product ID you want to order: ");
                                long productId = scanner.nextLong();
                                Optional<Product> productOptional = productRepository.findById(productId);

                                if (productOptional.isPresent()) {
                                    product = productOptional.get();

                                    quantity = 0;

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
                                System.out.println("Customer with ID " + userId + " not found.");
                            }
                            break;

                        case 2:
                            System.out.println("You have chosen the 'View Orders' option.");
                            userId = user.getId();
                            Optional<Order> customerOrders = orderRepository.findById(userId);

                            if (customerOrders.isEmpty()) {
                                System.out.println("No orders found for this user.");
                            } else {
                                System.out.println("Your orders:");
                                System.out.println("Orders for the user in JSON format:");
                                String ordersJson = orderService.convertOrdersToJsonArg(customerOrders);
                                System.out.println("Your orders in JSON format:");
                                System.out.println(ordersJson);

                                //Order customerOrder = customerOrders.get();

                                    *//*System.out.println("Bestellungs-ID: " + customerOrder.getId());
                                    System.out.println("Status: " + customerOrder.getStatus());
                                    System.out.println("Produkt: " + customerOrder.getProduct().getProductName());
                                    System.out.println("Menge: " + customerOrder.getQuantity());
                                    System.out.println("Customer ID: " + customerOrder.getUser().getId());*//*
                            }
                            break;
                        case 3:
                            System.out.println("Programm wird beendet.");
                            isRunning = false;
                            break;
                        default:
                            System.out.println("Invalid option. Please select a valid option");
                    }
                }
            }
    }

    //  scanner.close();


    }*/








