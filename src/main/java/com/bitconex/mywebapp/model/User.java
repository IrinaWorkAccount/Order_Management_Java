package com.bitconex.mywebapp.model;

import com.bitconex.mywebapp.security.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.springframework.lang.NonNull;

import java.util.Set;

/**
 * An abstract class that contains the common properties of users (Admin and Customer), such a login name, password and email.
 */

@Getter
@Setter
@Table(name = "users")
@MappedSuperclass
//@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING) // Added to distinguish the entity type

@Entity
public abstract class User {
    @Id
    //@Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Login", length = 128)
    @NonNull
    private String userLoginName;
    @Column(name = "email", unique=true)
    private String userEmail;
    @Column(name = "password")
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column (name = "role")
    private Role role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    //@JoinColumn(name="user_id")
    private Set<Order> orders;

    public User(@NonNull String userLoginName, String userEmail, String userPassword, Role role) {
        super();
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
    public void setId(long id) {
        this.id = id;
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

   /* public abstract boolean isAdmin();*/

   /* @Enumerated(EnumType.STRING)
    public void addRole(Role role){
        this.role=role;
    }*/


//abstract method
   /*public abstract String[] getFullUser();*/

    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }

    @Enumerated(EnumType.STRING)
    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Order> getOrders() {
        return orders;
    }
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}


