package com.bitconex.mywebapp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import static com.bitconex.mywebapp.security.Role.ADMIN;

/**
 * A subclass of "Users", which includes additional information for admin users.
 */

@Entity
@Table(name = "admins", schema = "public")
@DiscriminatorValue("ADMIN")
public class Admin extends User{

/*   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/
    public Admin(String userLoginName, String userEmail, String userPassword) {
        super(userLoginName, userEmail, userPassword, ADMIN);
    }

    public Admin() {
        //Default constructor
    }

   /* public Long getId() {
        return id;
    }*/

}