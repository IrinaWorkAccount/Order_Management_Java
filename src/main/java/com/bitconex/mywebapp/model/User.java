package com.bitconex.mywebapp.model;

import com.bitconex.mywebapp.security.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * The User class is an abstract class that serves as the base class for specific user types, such as `Admin` and `Customer`. It contains common properties like login name, email, and password, as well as the user's role and a list of associated orders.
 */

@Getter
@Setter
//@Table(name = "users")
//@MappedSuperclass
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING) // Added to distinguish the entity type

@Entity
@Table(name="user", schema = "public")
public abstract class User {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", length = 128)
    @NonNull
    private String userLogin;
    @Column(name = "email")//später hinzufügen : , unique = true
    //@Email(message = "Invalid email format")
    private String userEmail;
    @Column(name = "password")
    @JsonIgnore
    private String userPassword;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL) //mappedBy = "users" losgelassen, da es Fehler angezeigt wird
    @JoinColumn(name = "user_id")
    private List<Order> orders;

    /**
     * Constructs a new user with the specified login name, email, password, and role.
     *
     * @param userLogin    The user's login name.
     * @param userEmail    The user's email address.
     * @param userPassword The user's password.
     * @param role         The user's role (e.g., Admin or Customer).
     */
    public User(@NonNull String userLogin, String userEmail, String userPassword, Role role) {
        super();
        this.userLogin = userLogin;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.role = role;
    }

    /**
     * Default constructor for the `User` class.
     */
    public User() {
        //Default constructor
    }

    // Getters and setters for class properties
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(@NonNull String userLogin) {
        this.userLogin = userLogin;
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

    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }

    @Enumerated(EnumType.STRING)
    public void setRole(Role role) {
        this.role = role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}


