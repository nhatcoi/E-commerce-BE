package com.example.ecommerceweb.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop-details")
@RequiredArgsConstructor
public class ShopDetailsController {

    @GetMapping("")
    public String shopDetail() {
        return "shop-details";
    }
}
