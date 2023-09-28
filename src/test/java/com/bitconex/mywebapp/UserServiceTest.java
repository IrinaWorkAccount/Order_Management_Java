package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.User;
import com.bitconex.mywebapp.repository.UserRepository;
import com.bitconex.mywebapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListAllUsers() {
        List<User> mockUserList = new ArrayList<>();
        User mockUser1 = Mockito.mock(User.class);
        User mockUser2 = Mockito.mock(User.class);

        mockUserList.add(mockUser1);
        mockUserList.add(mockUser2);

        when(userRepository.findAll()).thenReturn(mockUserList);

        List<User> userList = userService.listAll();

        assertNotNull(userList);
        assertEquals(2, userList.size());

        verify(userRepository, times(1)).findAll();
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

        verify(userRepository, never()).findById(userId);
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