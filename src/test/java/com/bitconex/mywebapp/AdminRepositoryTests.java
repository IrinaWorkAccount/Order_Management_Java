package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Admin;
import com.bitconex.mywebapp.repository.AdminRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Objects;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class AdminRepositoryTests {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void testAddNewAdmin() {

        Admin admin1 = new Admin("userLoginName", "userEmail", "userPassword");
        Admin admin = adminRepository.save(admin1);

        Assertions.assertThat(admin).isNotNull();
        Assertions.assertThat(admin.getId()).isGreaterThan(0);
    }
    @Test
    public void testListAllAdmins(){
        Iterable<Admin> admins = adminRepository.findAll();
        Assertions.assertThat(admins).hasSizeGreaterThan(0);

        for (Admin admin: admins){
            System.out.println(admin);
        }
    }
    @Test
    public void testUpdateAdmin(){
        Integer adminID= 1;
                Optional<Admin> optionalAdmin=adminRepository.findById(adminID);
                Admin admin= optionalAdmin.orElse(null);
        if (admin.getUserPassword() != null) {
                admin.setUserPassword("querty2");}
                adminRepository.save(admin);

                Admin updatedAdmin=adminRepository.findById(adminID).orElse(null);
        if (updatedAdmin.getUserPassword() != null) {
                Assertions.assertThat(Objects.requireNonNull(updatedAdmin.getUserPassword())).isEqualTo("querty2"); }
    }
    @Test
    public void testGet(){
        Integer adminID  = 1;
        Optional<Admin> optionalAdmin=adminRepository.findById(adminID);

        Assertions.assertThat(optionalAdmin).isPresent();
        System.out.println(optionalAdmin.get());
    }

    @Test
    public void testDelete(){
        Integer adminID = 1;
        adminRepository.deleteById(adminID);

        Optional<Admin>optionalAdmin = adminRepository.findById(adminID);
        Assertions.assertThat(optionalAdmin).isNotPresent();
    }
}