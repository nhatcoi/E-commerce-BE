package com.example.ecommerceweb.controllers;

import com.example.ecommerceweb.dtos.ProductDTO;
import com.example.ecommerceweb.entities.Category;
import com.example.ecommerceweb.entities.Product;
import com.example.ecommerceweb.services.CategoryService;
import com.example.ecommerceweb.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("")
    public String home(@RequestParam(defaultValue = "0") int page, Model model) throws IOException, InterruptedException {
        List<Category> categories = categoryService.getAllCategories();

        Page<Product> productPage = productService.getProductsByPage(page, 8);
        List<Product> products = productPage.getContent();

        List<Product> sortedProducts = products.stream()
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .toList();

        List<Product> latestProductsLeft = sortedProducts.stream().limit(3).toList();
        List<Product> latestProductsRight = sortedProducts.stream().skip(3).limit(3).toList();

        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("latestProductsLeft", latestProductsLeft);
        model.addAttribute("latestProductsRight", latestProductsRight);

        return "index";
    }

}

