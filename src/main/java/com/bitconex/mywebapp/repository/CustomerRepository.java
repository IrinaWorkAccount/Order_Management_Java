package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    long countById(Long id);
   Optional<Customer> findByUserLoginName(String userLoginName);
}
