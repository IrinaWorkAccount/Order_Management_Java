package com.bitconex.mywebapp.model;

import jakarta.persistence.*;

/**
 * An abstract class that contains the common properties of users (Admin and Customer), such a login name, passwort and email.
 */

@Entity
@Table(name = "users")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@Column(name = "Login", length = 128, nullable = false )

    private String loginName;
    private String email;
    private String password;

    public User(String loginName, String email, String password) {
        this.loginName = loginName;
        this.email = email;
        this.password = password;
    }

    public User() {
        //TODO?
    }

    public String getUserLoginName() {
        return loginName;
    }

    public void setUserLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserEmail() {
        return email;
    }

    public void setUserEmail(String email) {
        this.email = email;
    }

    public String getUserPassword() {
        return password;
    }

    public void setUserPassword(String password) {
        this.password = password;
    }
}


