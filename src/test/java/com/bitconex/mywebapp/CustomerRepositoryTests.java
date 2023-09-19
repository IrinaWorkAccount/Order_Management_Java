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
import java.time.LocalDate;
import java.util.Optional;


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
        customer.setRole(Role.CUSTOMER);

        LocalDate birthDate = LocalDate.of(1990, 9, 14);
        customer.setCustomerBirthDate(Date.valueOf(birthDate).toLocalDate());

        CustomerAddress address = new CustomerAddress();
        address.setStreet("Feuer Str. 21");
        address.setZipCode("81546");
        address.setCity("Musterstadt");
        address.setCountry("Musterland");

        customer.setCustomerAddress(address);

        Customer savedCustomer = customerRepository.save(customer);

       /* boolean isNotAdmin = !savedCustomer.isAdmin();*/

        Assertions.assertThat(savedCustomer.getId()).isNotNull();
        /*Assertions.assertThat(isNotAdmin).isTrue();*/
    }
    @Test
    public void testListAllCustomers(){
        Iterable<Customer> customers = customerRepository.findAll();
        Assertions.assertThat(customers).hasSizeGreaterThan(0);

        for (Customer customer: customers){
            System.out.println(customer);
        }
    }

    @Test
    public void testUpdateCustomer(){
        Long customerID=1L;
        Optional<Customer> optionalCustomer=customerRepository.findById(customerID);
        Customer customer=optionalCustomer.orElse(null);
        assert customer !=null;

        CustomerAddress address = new CustomerAddress();
        address.setStreet("Locchauer Str. 8");
        address.setZipCode("12345");
        address.setCity("Musterstadt");
        address.setCountry("Musterland");

        customer.setCustomerAddress(address);

        customerRepository.save(customer);

        Customer updatedCustomer=customerRepository.findById(customerID).orElse(null);
        assert updatedCustomer !=null;

        CustomerAddress updatedAddress = updatedCustomer.getCustomerAddress();
        Assertions.assertThat(updatedAddress).isNotNull();
        Assertions.assertThat(updatedAddress.getStreet()).isEqualTo("Locchauer Str. 8");
        Assertions.assertThat(updatedAddress.getZipCode()).isEqualTo("12345");
        Assertions.assertThat(updatedAddress.getCity()).isEqualTo("Musterstadt");
        Assertions.assertThat(updatedAddress.getCountry()).isEqualTo("Musterland");
    }

@Test
public void testGet(){
        Long customerID = 1L;
        Optional<Customer> optionalCustomer=customerRepository.findById(customerID);

        Assertions.assertThat(optionalCustomer).isPresent();
    System.out.println(optionalCustomer.get());
}

@Test
public void testDelete() {
    Long customerID = 1L;
    customerRepository.deleteById(customerID);

    Optional<Customer> optionalCustomer = customerRepository.findById(customerID);
    Assertions.assertThat(optionalCustomer).isNotPresent();
}

}