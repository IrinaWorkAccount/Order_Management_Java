package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Admin;
import com.bitconex.mywebapp.repository.AdminRepository;
import com.bitconex.mywebapp.security.Role;
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
    private AdminRepository ar;

    @Test
    public void testAddNewAdmin() {

        Admin admin = new Admin();
        admin.setUserLoginName("userLoginName");
        admin.setUserEmail("userEmail");
        admin.setUserPassword("userPassword");
        admin.addRole(Role.ADMIN);

        Admin savedAdmin = ar.save(admin);

        Assertions.assertThat(savedAdmin).isNotNull();
        Assertions.assertThat(savedAdmin.getId()).isNotNull();
    }
    @Test
    public void testListAllAdmins(){
        Iterable<Admin> admins = ar.findAll();
        Assertions.assertThat(admins).hasSizeGreaterThan(0);

        for (Admin admin: admins){
            System.out.println(admin);
        }
    }
    @Test
    public void testUpdateAdmin(){
        Long adminID= 2L;
                Optional<Admin> optionalAdmin= ar.findById(adminID);
                Admin admin= optionalAdmin.orElse(null);
        assert admin != null;
        if (admin.getUserPassword() != null) {
                admin.setUserPassword("query2");}
                ar.save(admin);

                Admin updatedAdmin= ar.findById(adminID).orElse(null);
        assert updatedAdmin != null;
        if (updatedAdmin.getUserPassword() != null) {
                Assertions.assertThat(Objects.requireNonNull(updatedAdmin.getUserPassword())).isEqualTo("query2"); }
    }
    @Test
    public void testGet(){
        Long adminID  = 2L;
        Optional<Admin> optionalAdmin= ar.findById(adminID);

        Assertions.assertThat(optionalAdmin).isPresent();
        System.out.println(optionalAdmin.get());
    }

    @Test
    public void testDelete(){
        Long adminID = 1L;
        ar.deleteById(adminID);

        Optional<Admin>optionalAdmin = ar.findById(adminID);
        Assertions.assertThat(optionalAdmin).isNotPresent();
    }
}