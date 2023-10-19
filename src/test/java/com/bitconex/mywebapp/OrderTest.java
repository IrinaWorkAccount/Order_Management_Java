package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.Order;
import com.bitconex.mywebapp.model.Product;
import com.bitconex.mywebapp.model.User;
import com.bitconex.mywebapp.repository.OrderRepository;
import com.bitconex.mywebapp.security.Role;
import com.bitconex.mywebapp.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The `OrderTest` class contains test cases for the `OrderService` and `OrderRepository` classes.
 */
public class OrderTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test case to verify that an order is created and stored in the repository correctly.
    @Test
    public void testCreateOrderMock() {
        Product product = new Product();
        product.setProductName("productName_0");
        product.setProductSalePrice(1290.78);

        Customer mockUser = Mockito.mock();
        String status = "Pending";
        int quantity = 5;

        orderService.create(mockUser,quantity, product,status);

        final ArgumentCaptor<Order> captor
                = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());

        final Order savedOrder = captor.getValue();
        assertEquals(mockUser, savedOrder.getUser());
        assertEquals(product, savedOrder.getProduct());
    }

    // Integration test case to verify that an order is successfully stored in the database.
    @Test
    public void testCreateOrderIntegration() {
        Product product = new Product();

        product.setProductName("productName_0");
        product.setProductSalePrice(1290.78);
        Calendar today = Calendar.getInstance();
        product.setProductAvailableFrom(today.getTime());
        Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.YEAR, 1);
        product.setProductAvailableUntil(futureDate.getTime());

        Customer customer = new Customer();
        customer.setUserEmail("userEmail_1");
        customer.setUserLogin("userLoginName_1");
        customer.setUserPassword("userPassword_1" );
        customer.setCustomerName("Endera_1" );
        customer.setCustomerSurname("Hifhra_1" );
        customer.setRole(Role.CUSTOMER);
        Date birthDate1 = new Date(1990-9-14);
        customer.setCustomerBirthDate(birthDate1);

        Order order = new Order();
        String status = "Pending";

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.create(customer, 5, product, status);

        assertNotNull(createdOrder);
        assertEquals(customer, createdOrder.getUser());
        assertEquals(5, createdOrder.getQuantity());
        assertEquals(product, createdOrder.getProduct());
        assertEquals(status, createdOrder.getStatus());

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    // Test case to verify that an order is deleted from the repository.
    @Test
    public void testDeleteOrder() {
        Long orderId = 1L;
        Order mockOrder = new Order();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        orderService.delete(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }

    // Test case to verify that an exception is thrown when attempting to delete a non-existent order.
    @Test
    public void testDeleteOrderNotFound() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderService.delete(orderId));

        assertEquals("Order with ID 1 not found.", exception.getMessage());

        verify(orderRepository, never()).deleteById(orderId);
    }

    // Test case to verify that all orders are listed.
    @Test
    public void testListAllOrders() {
        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> orders = Arrays.asList(order1, order2);

        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.listAll();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    // Test case to verify that an order is retrieved by its ID.
    @Test
    public void testGetOrder() {
        Product product = new Product();
        product.setProductName("productName_0");
        product.setProductSalePrice(1290.78);

        Customer mockUser = Mockito.mock();
        String status = "Pending";
        int quantity = 5;
        Long id = 1L;

        orderService.create(mockUser, quantity, product, status);

        final ArgumentCaptor<Order> captor
                = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());

        final Order savedOrder = captor.getValue();
        savedOrder.setId(id);

        assertEquals(1L, savedOrder.getId());
    }

    // Test case to verify that an order's status is updated.
    @Test
    public void testUpdateOrderStatus() {
        Product product = new Product();
        product.setProductName("productName_0");
        product.setProductSalePrice(1290.78);

        Customer mockUser = Mockito.mock();
        String status = "Pending";
        int quantity = 5;


        orderService.create(mockUser, quantity, product, status);

        final ArgumentCaptor<Order> captor
                = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());

        final Order savedOrder = captor.getValue();
        savedOrder.setStatus("Confirmed");

        assertEquals("Confirmed", savedOrder.getStatus());
    }

    // Test case to verify that an order is saved.
    @Test
    public void testSaveOrder() {
        Product product = new Product();
        product.setProductName("productName_0");
        product.setProductSalePrice(1290.78);

        Customer mockUser = Mockito.mock();
        String status = "Pending";
        int quantity = 5;

        Order orderToCreate = orderService.create(mockUser, quantity, product, status);

        final ArgumentCaptor<Order> captor
                = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());

        final Order savedOrder = captor.getValue();
        assertEquals(savedOrder, orderToCreate);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    // Test case to verify the calculation of the total price for a user's orders.
    @Test
    public void testCalculateTotalPrice() {
        User user = new Customer();

        Order order1 = new Order();
        Order order2 = new Order();
        order1.setQuantity(2);
        order2.setQuantity(3);
        Product product = new Product();
        product.setProductSalePrice(10.0);
        order1.setProduct(product);
        order2.setProduct(product);
        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findAllByUserAndStatus(user, "In Progress")).thenReturn(orders);

        double totalPrice = orderService.calculateTotalPrice(user);
        assertEquals(50.0, totalPrice); // (2 * 10) + (3 * 10) = 50
    }

    // Test case to verify the conversion of orders to JSON format.
    @Test
    public void testConvertOrdersToJson() {
        Product product1 = new Product();
        product1.setProductName("productName_0");
        product1.setProductSalePrice(1290.78);

        Customer mockUser = new Customer();
        mockUser.setUserLogin("Alex");
        String status1 = "In Progress";
        int quantity1 = 5;

        Product product2 = new Product();
        product2.setProductName("productName_1");
        product2.setProductSalePrice(90);

        String status2 = "In Progress";
        int quantity2 = 1;

        Order order1 = orderService.create(mockUser, quantity1, product1, status1);
        Order order2 = orderService.create(mockUser, quantity2, product2, status2);

        when(orderService.listAll()).thenReturn(List.of(order1, order2));
        String jsonResult = null;
        try {
            jsonResult = orderService.convertOrdersToJson();
            System.out.println(jsonResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertEquals("[{\"id\":null,\"quantity\":5,\"product\":{\"id\":null,\"productName\":\"productName_0\",\"productSalePrice\":1290.78,\"productAvailableFrom\":null,\"productAvailableUntil\":null,\"productQuantity\":0},\"user\":{\"id\":null,\"userLogin\":\"Alex\",\"userEmail\":null,\"role\":null,\"customerName\":null,\"customerSurname\":null},\"status\":\"In Progress\"},{\"id\":null,\"quantity\":1,\"product\":{\"id\":null,\"productName\":\"productName_1\",\"productSalePrice\":90.0,\"productAvailableFrom\":null,\"productAvailableUntil\":null,\"productQuantity\":0},\"user\":{\"id\":null,\"userLogin\":\"Alex\",\"userEmail\":null,\"role\":null,\"customerName\":null,\"customerSurname\":null},\"status\":\"In Progress\"}]", jsonResult);
    }

}


