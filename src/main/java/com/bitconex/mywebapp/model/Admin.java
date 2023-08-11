package com.bitconex.mywebapp.model;

/**
 * A subclass of "Users", which includes additional information for admin users.
 */

public class Admin extends User {
    public Admin(String loginName, String email, String password) {
        super(loginName, email, password);
    }
    //Methods specific to Admin users

}