package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for the entity User.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserLogin(String userLogin);
}