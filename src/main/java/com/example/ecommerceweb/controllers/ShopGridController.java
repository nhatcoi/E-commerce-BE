package com.example.ecommerceweb.controllers;

import com.example.ecommerceweb.entities.Category;
import com.example.ecommerceweb.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/shop-grid")
@RequiredArgsConstructor
public class ShopGridController {
    private final CategoryService categoryService;

    @GetMapping("")
    public String shopGrid(Model model) {
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("categories", categories);
        return "shop-grid";
    }
}
