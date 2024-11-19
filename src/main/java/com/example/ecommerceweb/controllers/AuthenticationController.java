package com.example.ecommerceweb.controllers;

import com.example.ecommerceweb.dtos.request.AuthenticationRequest;
import com.example.ecommerceweb.dtos.response.ApiResponse;
import com.example.ecommerceweb.dtos.response.AuthenticationResponse;
import com.example.ecommerceweb.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/log-in")
    public ResponseEntity<AuthenticationResponse> logIn(@RequestBody  AuthenticationRequest request) {
        AuthenticationResponse result = authenticationService.authenticate(request);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                        .token(result.getToken())
                        .isAuthenticated(result.isAuthenticated())
                        .build()
        );
    }

}
