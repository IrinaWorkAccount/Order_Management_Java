package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.CustomerAddress;
import com.bitconex.mywebapp.repository.CustomerRepository;
import com.bitconex.mywebapp.security.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository cr;

    @Test
    public void testAddNewCustomer() {
        Customer customer = new Customer();
        customer.setUserLogin("Endera_Hifhra");
        customer.setUserEmail("Endera_Hifhra@gmail.com");
        customer.setUserPassword("querty1234");
        customer.setCustomerName("Endera");
        customer.setCustomerSurname("Hifhra");

        customer.setRole(Role.CUSTOMER);

        Date birthDate = new Date(1990-9-14);
        customer.setCustomerBirthDate(birthDate);

        CustomerAddress address = new CustomerAddress(null,null,null,null);
        address.setCity("Musterstadt");
        address.setCountry("Musterland");
        address.setStreet("Feuer Str. 21");
        address.setZipCode("81546");

        customer.setCustomerAddress(address);

        Customer savedCustomer = cr.save(customer);

        Assertions.assertThat(savedCustomer.getId()).isNotNull();
        
    }
    @Test
    public void testListAllCustomers(){
        Iterable<Customer> customers = cr.findAll();
        Assertions.assertThat(customers).hasSizeGreaterThan(0);

        for (Customer customer: customers){
            System.out.println(customer);
        }
    }

    @Test
    public void testUpdateCustomer() {
        Long customerID = 3L;
        Optional<Customer> optionalCustomer = cr.findById(customerID);
        Customer customer = optionalCustomer.orElse(null);
        assert customer != null;

        CustomerAddress address = new CustomerAddress(null,null,null,null);
        address.setStreet("Locchauer Str. 5");
        address.setZipCode("81755");
        address.setCity("Musterstadt");
        address.setCountry("Musterland");

        customer.setCustomerAddress(address);

        cr.save(customer);

        Customer updatedCustomer = cr.findById(customerID).orElse(null);
        assert updatedCustomer != null;

        CustomerAddress updatedAddress = updatedCustomer.getCustomerAddress();
        Assertions.assertThat(updatedAddress).isNotNull();
        Assertions.assertThat(updatedAddress.getStreet()).isEqualTo("Locchauer Str. 5");
        Assertions.assertThat(updatedAddress.getZipCode()).isEqualTo("81755");
        Assertions.assertThat(updatedAddress.getCity()).isEqualTo("Musterstadt");
        Assertions.assertThat(updatedAddress.getCountry()).isEqualTo("Musterland");
    }

@Test
public void testGet(){
    Long customerID = 3L;
    Optional<Customer> optionalCustomer = cr.findById(customerID);

        Assertions.assertThat(optionalCustomer).isPresent();
    System.out.println(optionalCustomer.get());
}

@Test
public void testDelete() {
    Long customerID = 2L;
    cr.deleteById(customerID);

    Optional<Customer> optionalCustomer = cr.findById(customerID);
    Assertions.assertThat(optionalCustomer).isNotPresent();
}

}