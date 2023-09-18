package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Customer;
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
import java.util.Objects;
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

        customer.setCustomerAddress("Feuer Str. 21, 81546");

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
        if (customer.getCustomerAddress()!= null){
            customer.setCustomerAddress("Locchauer Str.8");}
        customerRepository.save(customer);

        Customer updatedCustomer=customerRepository.findById(customerID).orElse(null);
        assert updatedCustomer !=null;
        if (updatedCustomer.getCustomerAddress() != null) {
            Assertions.assertThat(Objects.requireNonNull(updatedCustomer.getCustomerAddress())).isEqualTo("Locchauer Str.8"); }
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