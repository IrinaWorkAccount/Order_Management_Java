package com.bitconex.mywebapp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import static com.bitconex.mywebapp.security.Role.ADMIN;

/**
 * The `Admin` class is a subclass of "User" and represents an admin user in the system. It includes additional information specific to admin users.
 */
@Entity
@Table(name = "admins", schema = "public")
@DiscriminatorValue("ADMIN")
public class Admin extends User{

    /**
     * Constructs a new admin user with the specified properties.
     *
     * @param userLoginName The login name of the admin.
     * @param userEmail     The email of the admin.
     * @param userPassword  The password of the admin.
     */
/*   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/
    public Admin(String userLoginName, String userEmail, String userPassword) {
        super(userLoginName, userEmail, userPassword, ADMIN);
    }

    /**
     * Default constructor for the `Admin` class.
     */
    public Admin() {
        //Default constructor
    }

   /* public Long getId() {
        return id;
    }*/

}