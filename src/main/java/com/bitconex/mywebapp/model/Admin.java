package com.bitconex.mywebapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * A subclass of "Users", which includes additional information for admin users.
 */

@Entity
public class Admin extends User{

   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/

    public Admin() {}

    public Admin(String userLoginName, String userEmail, String userPassword) {
        setUserLoginName(userLoginName);
        setUserEmail(userEmail);
        setUserPassword(userPassword);
    }

   /* public Integer getId() {
        return id;
    }*/

    //Methods specific to Admin users
    @Override
    public boolean isAdmin() {
        return true; // An admin is always an administrator
    }

}