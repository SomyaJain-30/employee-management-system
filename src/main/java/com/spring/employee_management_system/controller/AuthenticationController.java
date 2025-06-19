package com.spring.employee_management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.employee_management_system.entity.User;
import com.spring.employee_management_system.service.UserService;

import jakarta.validation.Valid;

@RequestMapping("/auth")
@Controller
public class AuthenticationController {

    private UserService userService;
   
    @Autowired
    public AuthenticationController(UserService userService){
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/signup")
    public String showSignUp(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/login")
    public String showLogin(Model model){
        return "login";
    }

    @GetMapping("/userInfo")
    public String showUserInfo(Model model){
        return "user-info";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied(Model model){
        return "access-denied";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult){
        
        if(bindingResult.hasErrors()){
            return "signup";
        }

        User existingUser = userService.findByEmail(user.getEmail());
        if(existingUser != null){
            bindingResult.rejectValue("email", "duplicate.email", "Email already exists");
            return "signup";
        }
        userService.saveUser(user);
        return "login";
    }
}
