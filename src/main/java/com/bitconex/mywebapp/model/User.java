package com.bitconex.mywebapp.model;

import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * An abstract class that contains the common properties of users (Admin and Customer), such a login name, passwort and email.
 */

@Getter
@Setter
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
//@AllArgsConstructor // Hinzugefügt, um den Konstruktor automatisch zu generieren
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@Column(name = "Login", length = 128, nullable = false )
    private String userLoginName;
    @Column(name = "email")
    private String userEmail;
    @Column(name = "password")
    private String userPassword;

    // Konstruktor wird nicht mehr benötigt, da er durch Lombok generiert wird

    public User(String userLoginName, String userEmail, String userPassword) {
        this.userLoginName = userLoginName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public User() {
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String loginName) {
        this.userLoginName = loginName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String password) {
        this.userPassword = password;
    }

    public abstract boolean isAdmin();

}


