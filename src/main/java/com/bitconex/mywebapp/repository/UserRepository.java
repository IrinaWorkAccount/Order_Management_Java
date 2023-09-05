package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
