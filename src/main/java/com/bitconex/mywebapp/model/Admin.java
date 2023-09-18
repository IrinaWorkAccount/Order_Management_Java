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
   /* public Admin(String[] anArray){
        super(anArray[0],anArray[1],anArray[2], Role.valueOf(anArray[3]));
    }*/



    public Admin() {
        //Default constructor
    }

    /*public String[] getFullUser() {
        String[] FullAdminInfo = new String[5];
        FullAdminInfo[0] = String.valueOf(super.getId());
        FullAdminInfo[1] = super.getUserLoginName();
        FullAdminInfo[2] = super.getUserEmail();
        FullAdminInfo[3] = super.getUserPassword();
        FullAdminInfo[4] = String.valueOf(super.getRole());
        return FullAdminInfo;
    }*/

    /*public String ToString(){
        return super.toString()+"role : "+ ADMIN;
    }*/

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