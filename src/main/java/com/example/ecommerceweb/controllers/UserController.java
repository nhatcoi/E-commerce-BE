package com.example.ecommerceweb.controllers;

import com.example.ecommerceweb.dtos.UserResponse;
import com.example.ecommerceweb.services.UserService;
import com.example.ecommerceweb.dtos.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login-form")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.getUserByPhoneNumberAndPassword(
                userRequest.getPhoneNumber(),
                userRequest.getPassword());
        return ResponseEntity.ok().body(userResponse);
    }

}
