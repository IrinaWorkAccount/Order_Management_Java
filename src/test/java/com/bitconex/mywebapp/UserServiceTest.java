package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Admin;
import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.CustomerAddress;
import com.bitconex.mywebapp.model.User;
import com.bitconex.mywebapp.repository.UserRepository;
import com.bitconex.mywebapp.security.Role;
import com.bitconex.mywebapp.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bitconex.mywebapp.security.Role.ADMIN;
import static com.bitconex.mywebapp.security.Role.CUSTOMER;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    private Customer customer;
    private User user;
    private Admin admin;
    private final List<Customer> customerList = new ArrayList<>();
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCustomer() {

    }

    @Test
    public void testListAllJSOn() {
        Admin admin1 = new Admin();
        admin1.setId(7L);
        admin1.setUserEmail("userEmail_1");
        admin1.setUserLoginName("userLoginName_1");
        admin1.setUserPassword("userPassword_1");
        admin1.addRole(Role.ADMIN);
        Admin admin2 = new Admin();
        admin2.setId(8L);
        admin2.setUserEmail("userEmail_2");
        admin2.setUserLoginName("userLoginName_2");
        admin2.setUserPassword("userPassword_2");
        admin2.addRole(Role.ADMIN);
        when(userService.listAll()).thenReturn(List.of(admin1, admin2));
        String jsonResult = null;
        try {
            jsonResult = userService.listAllJSOn();
            System.out.println(jsonResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertEquals("[{\"id\":7,\"userLoginName\":\"userLoginName_1\",\"userEmail\":\"userEmail_1\",\"userPassword\":\"userPassword_1\",\"role\":\"ADMIN\",\"orders\":null},{\"id\":8,\"userLoginName\":\"userLoginName_2\",\"userEmail\":\"userEmail_2\",\"userPassword\":\"userPassword_2\",\"role\":\"ADMIN\",\"orders\":null}]",
        jsonResult);
    }


    @Test
    public void testListAllCusts() {
        Customer customer1 = new Customer();
        customer1.setUserEmail("userEmail_1");
        customer1.setUserLoginName("userLoginName_1");
        customer1.setUserPassword("userPassword_1" );
        customer1.setCustomerName("Endera_1" );
        customer1.setCustomerSurname("Hifhra_1" );
        customer1.addRole(Role.CUSTOMER);
        LocalDate birthDate1 = LocalDate.of(1990, 9, 14);
        customer1.setCustomerBirthDate(Date.valueOf(birthDate1).toLocalDate());

        CustomerAddress address1 = new CustomerAddress();
        address1.setCity("Musterstadt");
        address1.setCountry("Musterland");
        address1.setStreet("Muster Str. 21");
        address1.setZipCode("81546");

        customer1.setCustomerAddress(address1);

        Customer customer2 = new Customer();
        customer2.setUserEmail("userEmail_1");
        customer2.setUserLoginName("userLoginName_1");
        customer2.setUserPassword("userPassword_1" );
        customer2.setCustomerName("Endera_1" );
        customer2.setCustomerSurname("Hifhra_1" );
        customer2.addRole(Role.CUSTOMER);
        LocalDate birthDate2= LocalDate.of(1980, 7, 10);
        customer2.setCustomerBirthDate(Date.valueOf(birthDate2).toLocalDate());

        CustomerAddress address2 = new CustomerAddress();
        address2.setCity("Musterstadt");
        address2.setCountry("Musterland");
        address2.setStreet("Muster Str. 21");
        address2.setZipCode("81546");

        customer2.setCustomerAddress(address2);

        when(userService.listAll()).thenReturn(List.of(customer1,customer2));
        var result = userService.listAll();

        assertNotNull(result);
        assertEquals(2,result.size());

        assertEquals(customer1,result.get(0));
        assertEquals(customer2,result.get(1));

    }

    @Test
    public void testListAllAdmins() {
        Admin admin1 = new Admin();
        admin1.setUserEmail("userEmail_1");
        admin1.setUserLoginName("userLoginName_1");
        admin1.setUserPassword("userPassword_1");
        admin1.addRole(Role.ADMIN);
        Admin admin2 = new Admin();
        admin2.setUserEmail("userEmail_2");
        admin2.setUserLoginName("userLoginName_2");
        admin2.setUserPassword("userPassword_2");
        admin2.addRole(Role.ADMIN);
        when(userService.listAll()).thenReturn(List.of(admin1,admin2));
        var result = userService.listAll();

        assertNotNull(result);
        assertEquals(2,result.size());
        assertEquals(admin1,result.get(0));
        assertEquals(admin2,result.get(1));
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User mockUser = Mockito.mock(User.class); // Create a mock User object

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        User retrievedUser = userService.get(userId);

        assertNotNull(retrievedUser);

        verify(userRepository, times(1)).findById(userId);
    }



    @Test
    public void testGetUserByIdNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.get(userId);
        });

        assertEquals("Could not find any users with ID 1", exception.getMessage());

    }

    @Test
    public void testDeleteUser() {
        String loginName = "testUser";
        User mockUser = Mockito.mock(User.class); // Create a mock User object

        when(userRepository.findByUserLoginName(loginName)).thenReturn(Optional.of(mockUser));

        userService.delete(loginName);

        verify(userRepository, times(1)).delete(mockUser);
    }

    @Test
    public void testDeleteUserNotFound() {
        String loginName = "nonExistentUser";

        when(userRepository.findByUserLoginName(loginName)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.delete(loginName);
        });

        assertEquals("User with ID nonExistentUser not found.", exception.getMessage());

        verify(userRepository, never()).delete(any(User.class));
    }
}