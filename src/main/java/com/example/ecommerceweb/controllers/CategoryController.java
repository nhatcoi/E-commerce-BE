package com.example.ecommerceweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class CategoryController {


    @PostMapping("")
    public String createCategory() {
        return "redirect:/";
    }

}
