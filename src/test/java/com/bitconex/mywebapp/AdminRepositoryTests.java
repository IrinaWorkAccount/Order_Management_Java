package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Admin;
import com.bitconex.mywebapp.repository.AdminRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class AdminRepositoryTests {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void testAddNewAdmin() {
        Admin admin = adminRepository.save(new Admin("userLoginName", "userEmail", "userPassword"));

        Assertions.assertThat(admin).isNotNull();
        Assertions.assertThat(admin.getId()).isGreaterThan(0);
    }
}