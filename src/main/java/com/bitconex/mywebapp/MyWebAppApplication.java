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
            adminPanel(scanner);
        } else if (user.getRole() == Role.CUSTOMER) {
            customerPanel(user, scanner);
        }
    }

    // Admin Panel
    private void adminPanel(Scanner scanner) throws JsonProcessingException {
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
                    createUser(scanner);
                    break;
                case 2: // List all users
                    listAllUsers();
                    break;
                case 3: // Delete a user
                    deleteUser(scanner);
                    break;
                case 4: // Product Catalog
                    productCatalog(scanner);
                    break;
                case 5: // List all orders
                    listAllOrders();
                    break;
                case 6: // Exit program
                    return;
                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }

    private void createUser(Scanner scanner) {
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

    private void listAllUsers() throws JsonProcessingException {
        String usersJson = userService.listAllJSOn();
        System.out.println("List of all users in JSON format:");
        System.out.println(usersJson);
    }

    private void deleteUser(Scanner scanner) {
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
                    createProduct(scanner);
                    break;
                case 2: // List all products
                    listAllProducts();
                    break;
                case 3: // Delete a product
                    deleteProduct(scanner);
                    break;
                case 4: // Back to Administration
                    return;
                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }

    // Create a new product
    private void createProduct(Scanner scanner) {
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
    private void listAllProducts() throws JsonProcessingException {
        String productsJson = productService.listAllProductsInJson();
        System.out.println("List of all products in the catalog in JSON format:");
        System.out.println(productsJson);
    }

    // Delete a product
    private void deleteProduct(Scanner scanner) {
        System.out.print("Enter the ID of the product to delete: ");
        long productIdToDelete = scanner.nextLong();
        scanner.nextLine();
        productService.delete(productIdToDelete);
        System.out.println("Product deleted successfully.");
    }

    // List all orders
    private void listAllOrders() throws JsonProcessingException {
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
                    placeOrder(scanner, user);
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


    private void placeOrder(Scanner scanner, User user) {
        long userId = user.getId();
        Optional<Customer> customerOptional = customerRepository.findById(userId);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            boolean addingMoreProducts = true;

            while (addingMoreProducts) {
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

                    orderService.create(customer, quantity, product, "In Progress");
                    System.out.println("Product added to the order.");

                    // Ask the user if they want to add more products or confirm the order
                    System.out.print("Options: 1 for confirm, 2 for add another product: ");
                    int option = scanner.nextInt();
                    if (option == 1) {
                        addingMoreProducts = false; // Exit the loop if the user confirms
                    }

                    // If option is 2, continue the loop to add another product

                    // Calculate the total price for the current order
                    double totalPrice = orderService.calculateTotalPrice(user);
                    List<Order> lastOrders = orderService.findLastOrderForUser(user);
                    // The list of recent orders along with individual prices and the total prices
                    System.out.println("Last Orders for User " + user.getUserLogin() + ":");
                    for (Order order : lastOrders) {
                        double orderTotal = order.getProduct().getProductSalePrice() * order.getQuantity();
                        System.out.println("Product Name: " + order.getProduct().getProductName());
                        System.out.println("Quantity: " + order.getQuantity());
                        System.out.println("Unit price: " + order.getProduct().getProductSalePrice());
                        System.out.println("Individual Price: $" + orderTotal);
                        System.out.println("-----");
                    }
                    System.out.println("The total sum for all your orders: $" + totalPrice);
                } else {
                    System.out.println("Product with ID " + productId + " not found.");
                }
            }
        } else {
            System.out.println("Customer with ID " + userId + " not found.");
        }
        }


    private void viewOrders(OrderService orderService, User user) throws JsonProcessingException {
        Long userId = user.getId();
        if (userId != null) {
            String ordersJson = orderService.getOrdersJsonForUser(user);
            System.out.println("Your orders in JSON format:");
            System.out.println(ordersJson);
        }
    }
}








