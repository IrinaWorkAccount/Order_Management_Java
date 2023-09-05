package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;
import java.time.LocalDate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testAddNewCustomer() {
        Customer customer = new Customer();
        customer.setUserLoginName("Endera_Hifhra");
        customer.setUserEmail("Endera_Hifhra@gmail.com");
        customer.setUserPassword("querty1234");
        customer.setCustomerName("Endera");
        customer.setCustomerSurname("Hifhra");

        LocalDate birthDate = LocalDate.of(1990, 9, 14);
        customer.setCustomerBirthDate(Date.valueOf(birthDate).toLocalDate());

        customer.setCustomerAddress("Feuer Str. 21, 81546");

        Customer savedCustomer = customerRepository.save(customer);

        boolean isNotAdmin = !savedCustomer.isAdmin();

        Assertions.assertThat(savedCustomer.getId()).isGreaterThan(0);
        Assertions.assertThat(isNotAdmin).isTrue();
    }
}