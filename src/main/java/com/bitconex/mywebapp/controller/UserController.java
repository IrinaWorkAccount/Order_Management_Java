package com.bitconex.mywebapp.controller;


import com.bitconex.mywebapp.model.User;
import com.bitconex.mywebapp.security.Role;
import com.bitconex.mywebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * A class that implement user management functions, such as an adding, listing and deleting users.
 *
 * @autor Irina Barvenko
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public List<User>  showUserList(Model model) {
        List<User> listUsers = userService.listAll();
        //model.addAttribute("listUsers", listUsers);

        return listUsers;
    }

    @RequestMapping("/users/delete/{login}")
    public String deleteUser(@PathVariable String login){//, RedirectAttributes ra) {
           // try {
                userService.delete(login);
                return "The user login" + login + " has been deleted. ";
            //    ra.addFlashAttribute("message", "The user with login name " + login + " has been deleted.");
            /*} catch (Exception e) {
                ra.addFlashAttribute("message", e.getMessage());
            }*/
        }

@RequestMapping("/users/show/{id}")
    public User showUser(@PathVariable Long id){
       return userService.get(id);
}

@RequestMapping(method=RequestMethod.POST, value="/users")
    public void addUser(@RequestBody User user){
        userService.save(user);
}
    }


/*    @GetMapping("/")
    public void save() {
        User user = new User();
        user.setRole(Role.CUSTOMER);
        userService.save(user);
    }*/



