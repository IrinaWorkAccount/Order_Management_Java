package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Admin;
import com.bitconex.mywebapp.model.User;
import org.springframework.stereotype.Service;
@Service
public class AdminManagementService extends UserService{

    @Override
    public void save(User user) {
        if (user instanceof Admin) {
        }
        super.save(user);
    }
}
