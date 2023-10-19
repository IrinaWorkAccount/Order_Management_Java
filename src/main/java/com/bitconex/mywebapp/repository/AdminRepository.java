package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The `AdminRepository` is a repository interface for the `Admin` entity. It provides access to basic CRUD (Create, Read, Update, Delete) operations for `Admin` entities.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    // No additional methods are defined in this repository.
}
