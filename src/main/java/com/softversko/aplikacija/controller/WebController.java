package com.softversko.aplikacija.controller;


import com.softversko.aplikacija.model.Item;
import com.softversko.aplikacija.model.User;
import com.softversko.aplikacija.service.AuthService;
import com.softversko.aplikacija.service.ItemService;
import com.softversko.aplikacija.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    ItemService itemService;

    @Autowired
    AuthService authService;

    @GetMapping("/")
    String index(Model model){
        List <Item> items = itemService.findAll();
        if(authService.isAuthenticated())
            model.addAttribute("username",authService.getUsername());
        model.addAttribute("items",items);
        return "index.html";
    }

    @GetMapping("/register")
    String register(){
        return "register.html";
    }

    @GetMapping("/add")
    String addItem(Model model){
        if(authService.isAuthenticated())
            model.addAttribute("username",authService.getUsername());
        return "add.html";
    }



}
