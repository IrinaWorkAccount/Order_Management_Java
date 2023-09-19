package com.bitconex.mywebapp.model;

import com.bitconex.mywebapp.security.Role;
import jakarta.persistence.*;

import java.time.LocalDate;

import static com.bitconex.mywebapp.security.Role.ADMIN;

/**
 * A subclass of "Users", which includes additional information for admin users.
 */

@Entity (name = "Admin")
@DiscriminatorValue("ADMIN")
public class Admin extends User{
    /**
     * @param id
     * @param userLoginName The admins login name that extends from the user class
     * @param userEmail The user e-mail that extends from the user class
     * @param userPassword The user password that extends from the user class
    */
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Admin(String userLoginName, String userEmail, String userPassword) {
        super(userLoginName, userEmail, userPassword, ADMIN);
    }

    public Admin() {
        //Default constructor
    }

        public Long getId() {
        return id;
    }

    //Methods specific to Admin users
   /* @Override
    public boolean isAdmin() {
        return true; // An admin is always an administrator
    }
*/
}