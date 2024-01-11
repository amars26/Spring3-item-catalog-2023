package com.softversko.aplikacija.controller;

import com.softversko.aplikacija.model.User;
import com.softversko.aplikacija.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/process_register")
    public String processRegistration(User user) {
        userService.registerUser(user);
        return "redirect:/";
    }

}
