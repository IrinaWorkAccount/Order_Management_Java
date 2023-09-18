package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.Admin;
import com.bitconex.mywebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    long countById(Long id);
}
