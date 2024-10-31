package com.example.ecommerceweb.controllers;

import com.example.ecommerceweb.entities.ProductRating;
import com.example.ecommerceweb.services.ProductService;
import com.example.ecommerceweb.services.services_impl.ProductImageService;
import com.example.ecommerceweb.services.services_impl.ProductRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("products")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductImageService productImageService;
    private final ProductRatingService productRatingService;

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("productImages", productImageService.getProductImagesByProductId(id));
        model.addAttribute("avgRating", productRatingService.avgRating(id));
//        model.addAttribute("textChatGPT", textChatgptService.getChatGPTResponse());
        return "shop-details";
    }

}