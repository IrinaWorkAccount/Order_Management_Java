package com.bitconex.mywebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * * A class that implements the REST/HTTP/JSON endpoints to enable external integrations.
 */

@Controller
public class RestAPIController {
    @GetMapping("")
    public String showHomePage(){
        System.out.println("main controller");
        return "index";
    }

}


