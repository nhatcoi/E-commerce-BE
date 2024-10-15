package com.example.ecommerceweb.controllers;

import com.example.ecommerceweb.dtos.CategoryDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("categories")
public class CategoryController {


    @PostMapping("")
    public String createCategory(@ModelAttribute CategoryDTO categoryDTO) {
        return "redirect:/";
    }

    @GetMapping("")
    public String getAllCategories() {
        return "categories";
    }

    @PutMapping("/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute CategoryDTO categoryDTO) {
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        return "redirect:/";
    }
}
