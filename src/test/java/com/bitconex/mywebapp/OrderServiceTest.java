package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.Order;
import com.bitconex.mywebapp.model.Product;
import com.bitconex.mywebapp.repository.OrderRepository;
import com.bitconex.mywebapp.repository.UserRepository;
import com.bitconex.mywebapp.security.Role;
import com.bitconex.mywebapp.service.OrderService;
import com.bitconex.mywebapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;
    @InjectMocks
    private UserService userService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //The test verifies that repository.save has been called with an order that has the user and product set correctly.
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

    //The integration test verifies whether the entity is successfully stored in the database.
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


        //verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testDeleteOrder() {
        Long orderId = 1L;
        Order mockOrder = new Order();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        orderService.delete(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    public void testDeleteOrderNotFound() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.delete(orderId);
        });

        assertEquals("Order with ID 1 not found.", exception.getMessage());

        verify(orderRepository, never()).deleteById(orderId);
    }
}
