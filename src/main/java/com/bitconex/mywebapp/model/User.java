package com.bitconex.mywebapp.model;

import com.bitconex.mywebapp.security.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.springframework.lang.NonNull;

/**
 * An abstract class that contains the common properties of users (Admin and Customer), such a login name, password and email.
 */

@Getter
@Setter
//@Table(name = "users")
@MappedSuperclass
//@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING) // Added to distinguish the entity type




public abstract class User {
    @Id
    //@Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Login", length = 128)
    @NonNull
    private String userLoginName;
    @Column(name = "email")
    private String userEmail;
    @Column(name = "password")
    private String userPassword;

    private Role role;

    public User(@NonNull String userLoginName, String userEmail, String userPassword, Role role) {
        this.userLoginName = userLoginName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.role = role;
    }

    public User() {
        //Default constructor
    }

    public Long getId() {
        return id;
    }

    @NonNull
    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(@NonNull String loginName) {
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

    public void addRole(Role role){
        this.role=role;
    }


}


