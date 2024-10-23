package com.example.ecommerceweb.controllers;

import com.example.ecommerceweb.dtos.CategoryDTO;
import com.example.ecommerceweb.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    public String createCategory(@ModelAttribute CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
        return "redirect:/";
    }

    @GetMapping("")
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "index";
    }

    @PutMapping("/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return "redirect:/";
    }
}
