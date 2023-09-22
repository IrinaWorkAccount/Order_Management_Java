package com.bitconex.mywebapp.service;

import com.bitconex.mywebapp.model.Admin;
import com.bitconex.mywebapp.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService{

    @Autowired
    private AdminRepository ar;

    public void save(Admin admin) {
        ar.save(admin);
    }
    public List<Admin> listAll() {
        return (List<Admin>) ar.findAll();
    }

    public Admin get(Long id) throws Exception {
        Optional<Admin> existingAdmin = ar.findById(id);
        if (existingAdmin.isPresent()) {
            return existingAdmin.get();
        }
        throw new Exception("Could not find any admins with ID " + id);
    }

    public void delete(Long id) throws Exception {
        Long count = ar.countById(id);
        if (count == null || count == 0) {
            throw new Exception("Could not find any admin with ID " + id);
        }ar.deleteById(id);
    }
}
