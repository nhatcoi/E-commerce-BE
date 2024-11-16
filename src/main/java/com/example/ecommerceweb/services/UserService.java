package com.example.ecommerceweb.services;

import com.example.ecommerceweb.dtos.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserResponse getUserByPhoneNumberAndPassword(String phoneNumber, String password);
}
