package com.example.ecommerceweb.controllers;

import com.example.ecommerceweb.entities.Product;
import com.example.ecommerceweb.services.ProductService;
import com.example.ecommerceweb.services.services_impl.ProductImageService;
import com.example.ecommerceweb.services.services_impl.ProductRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RequestMapping("products")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductImageService productImageService;
    private final ProductRatingService productRatingService;

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) throws IOException {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("productImages", productImageService.getProductImagesByProductId(id));
        model.addAttribute("avgRating", productRatingService.avgRating(id));
        return "shop-details";
    }

    @GetMapping("product/share")
    public String getProductShare(@RequestParam Long id, Model model) {
        //get url product
        String urlProduct = ServletUriComponentsBuilder.fromCurrentContextPath().path("/products/").path(id.toString()).toUriString();
        model.addAttribute("urlProduct", urlProduct);
        return "common/share-product";
    }
}