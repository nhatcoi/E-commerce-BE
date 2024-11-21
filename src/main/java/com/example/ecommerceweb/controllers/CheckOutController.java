package com.example.ecommerceweb.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("checkout")
@RequiredArgsConstructor
public class CheckOutController {

    @GetMapping("")
    public String checkOut() {
        return "checkout";
    }
}
