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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The `UserTest` class contains test cases for the `UserService` and `UserRepository` classes.
 */
public class UserTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test case to verify that a JSON representation of all users (both admins and customers) is generated correctly.
    @Test
    public void testListAllJSON() {
        Admin admin1 = new Admin();
        admin1.setId(7L);
        admin1.setUserEmail("userEmail_1");
        admin1.setUserLogin("userLoginName_1");
        admin1.setUserPassword("userPassword_1");
        admin1.setRole(Role.ADMIN);
        Admin admin2 = new Admin();
        admin2.setId(8L);
        admin2.setUserEmail("userEmail_2");
        admin2.setUserLogin("userLoginName_2");
        admin2.setUserPassword("userPassword_2");
        admin2.setRole(Role.ADMIN);
        when(userService.listAll()).thenReturn(List.of(admin1, admin2));
        String jsonResult = null;
        try {
            jsonResult = userService.listAllJSOn();
            System.out.println(jsonResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertEquals("[{\"id\":7,\"userLogin\":\"userLoginName_1\",\"userEmail\":\"userEmail_1\",\"role\":\"ADMIN\"},{\"id\":8,\"userLogin\":\"userLoginName_2\",\"userEmail\":\"userEmail_2\",\"role\":\"ADMIN\"}]",
                jsonResult);
    }

    // Test case to verify that a list of all customer users is generated correctly.
    @Test
    public void testListAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setUserEmail("userEmail_1");
        customer1.setUserLogin("userLoginName_1");
        customer1.setUserPassword("userPassword_1");
        customer1.setCustomerName("Endera_1");
        customer1.setCustomerSurname("Hifhra_1");
        customer1.setRole(Role.CUSTOMER);
        Date birthDate1 = new Date(1990 - 9 - 14);
        customer1.setCustomerBirthDate(birthDate1);

        CustomerAddress address1 = new CustomerAddress(null,null,null,null);
        address1.setCity("Musterstadt");
        address1.setCountry("Musterland");
        address1.setStreet("Muster Str. 21");
        address1.setZipCode("81546");

        customer1.setCustomerAddress(address1);

        Customer customer2 = new Customer();
        customer2.setUserEmail("userEmail_1");
        customer2.setUserLogin("userLoginName_1");
        customer2.setUserPassword("userPassword_1" );
        customer2.setCustomerName("Endera_1" );
        customer2.setCustomerSurname("Hifhra_1" );
        customer2.setRole(Role.CUSTOMER);
        Date birthDate2 = new Date(1980-7-10);
        customer2.setCustomerBirthDate(birthDate2);

        CustomerAddress address2 = new CustomerAddress(null,null,null,null);
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

    // Test case to verify that a list of all admin users is generated correctly.
    @Test
    public void testListAllAdmins() {
        Admin admin1 = new Admin();
        admin1.setUserEmail("userEmail_1");
        admin1.setUserLogin("userLoginName_1");
        admin1.setUserPassword("userPassword_1");
        admin1.setRole(Role.ADMIN);
        Admin admin2 = new Admin();
        admin2.setUserEmail("userEmail_2");
        admin2.setUserLogin("userLoginName_2");
        admin2.setUserPassword("userPassword_2");
        admin2.setRole(Role.ADMIN);
        when(userService.listAll()).thenReturn(List.of(admin1,admin2));
        var result = userService.listAll();

        assertNotNull(result);
        assertEquals(2,result.size());
        assertEquals(admin1,result.get(0));
        assertEquals(admin2,result.get(1));
    }

    // Test case to verify that a user is retrieved by ID.
    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User mockUser = Mockito.mock(User.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        User retrievedUser = userService.get(userId);

        assertNotNull(retrievedUser);

        verify(userRepository, times(1)).findById(userId);
    }

    // Test case to verify that an exception is thrown when attempting to retrieve a non-existent user by ID.
    @Test
    public void testGetUserByIdNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.get(userId));

        assertEquals("Could not find any users with ID 1", exception.getMessage());
    }

    // Test case to verify that a user is deleted by login name.
    @Test
    public void testDeleteUser() {
        String loginName = "testUser";
        User mockUser = Mockito.mock(User.class);

        when(userRepository.findByUserLogin(loginName)).thenReturn(Optional.of(mockUser));

        userService.delete(loginName);

        verify(userRepository, times(1)).delete(mockUser);
    }

    // Test case to verify that an exception is thrown when attempting to delete a non-existent user by login name.
    @Test
    public void testDeleteUserNotFound() {
        String loginName = "nonExistentUser";

        when(userRepository.findByUserLogin(loginName)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.delete(loginName));

        assertEquals("User with ID nonExistentUser not found.", exception.getMessage());

        verify(userRepository, never()).delete(any(User.class));
    }

    // Test case to verify that an admin user is updated correctly.
    @Test
    public void testUpdateAdmin() {
        Admin admin1 = new Admin();
        admin1.setId(7L);
        admin1.setUserEmail("userEmail_1");
        admin1.setUserLogin("userLoginName_1");
        admin1.setUserPassword("userPassword_1");
        admin1.setRole(Role.ADMIN);

        admin1.setUserEmail("userEmailNew");
        admin1.setUserLogin("userLoginNew");
        admin1.setUserPassword("query");

        assertEquals("userEmailNew", admin1.getUserEmail());
        assertEquals("userLoginNew", admin1.getUserLogin());
        assertEquals("query", admin1.getUserPassword());
    }

}