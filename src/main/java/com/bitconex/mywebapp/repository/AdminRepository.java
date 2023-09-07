package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    public Integer countById(Integer id);
}
