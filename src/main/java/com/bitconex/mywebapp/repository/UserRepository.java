package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserLoginName(String userLoginName);
}