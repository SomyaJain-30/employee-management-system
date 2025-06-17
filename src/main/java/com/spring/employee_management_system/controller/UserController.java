package com.spring.employee_management_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
@Controller
public class UserController {

    @GetMapping("/")
    public String showAllUsers(){
        return "list-users";
    }

}
