package com.example.ecommerceweb.services.services_impl;

import com.example.ecommerceweb.dtos.UserResponse;
import com.example.ecommerceweb.entities.User;
import com.example.ecommerceweb.repository.UserRepository;
import com.example.ecommerceweb.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public UserResponse getUserByPhoneNumberAndPassword(String phoneNumber, String password) {
        User user = userRepository.findByPhoneNumberAndPassword(phoneNumber, password);
        if (user != null) {
            return UserResponse.builder()
                    .id(user.getId())
                    .fullName(user.getFullName())
                    .phoneNumber(user.getPhoneNumber())
                    .password(user.getPassword())
                    .address(user.getAddress())
                    .build();
        }
        return null;
    }
}
