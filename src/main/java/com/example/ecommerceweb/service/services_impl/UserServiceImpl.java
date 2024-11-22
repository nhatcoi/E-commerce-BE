package com.example.ecommerceweb.service.services_impl;

import com.example.ecommerceweb.dto.request.UserRequest;
import com.example.ecommerceweb.dto.response.UserResponse;
import com.example.ecommerceweb.entity.Role;
import com.example.ecommerceweb.entity.User;
import com.example.ecommerceweb.exception.ResourceNotFoundException;
import com.example.ecommerceweb.repository.UserRepository;
import com.example.ecommerceweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
                    .address(user.getAddress())
                    .build();
        }
        return null;
    }


    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByPhoneNumber(userRequest.getPhoneNumber())) {
            throw new ResourceNotFoundException("Phone number already exists");
        }

        User user = User.builder()
                .phoneNumber(userRequest.getPhoneNumber())
                .password(userRequest.getPassword())
                .role(Role.builder().id(com.example.ecommerceweb.enums.Role.USER.getValue()).build())
                .build();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .roleId(Math.toIntExact(user.getRole().getId()))
                .build();
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .fullName(user.getFullName())
                        .phoneNumber(user.getPhoneNumber())
                        .address(user.getAddress())
                        .roleId(Math.toIntExact(user.getRole().getId()))
                        .build())
                .toList();
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .roleId(Math.toIntExact(user.getRole().getId()))
                .build();
    }
}
