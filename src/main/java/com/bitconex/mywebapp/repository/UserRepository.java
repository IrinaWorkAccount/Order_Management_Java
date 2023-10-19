package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The `UserRepository` is a repository interface for the `User` entity. It extends the `JpaRepository` interface and provides access to basic CRUD (Create, Read, Update, Delete) operations for `User` entities. Additionally, it defines a custom query method to search for a user by their login name.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Retrieves an optional `User` entity by their user login name.
     *
     * @param userLogin The login name of the user to search for.
     * @return An optional `User` entity that matches the provided user login name, or an empty optional if no match is found.
     */
    Optional<User> findByUserLogin(String userLogin);
}