package com.example.ecommerceweb.services;

import com.example.ecommerceweb.dtos.request.AuthenticationRequest;
import com.example.ecommerceweb.dtos.response.AuthenticationResponse;
import com.example.ecommerceweb.entities.User;
import com.example.ecommerceweb.exceptions.ResourceNotFoundException;
import com.example.ecommerceweb.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    @NonFinal
    protected static final String SIGNER_KEY =
            "iQOECQikAS3X06CvIy/C4Y3mzl4ZHyHEHLh66bIwKDNp+fiS1ujz5r0BXmCh8BDi";

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated)
            throw new ResourceNotFoundException("Unauthenticated");

        var token = generateToken(user.getPhoneNumber());

        return AuthenticationResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();

    }


    private String generateToken(String phoneNumber) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimSet = new JWTClaimsSet.Builder()
                .subject(phoneNumber)
                .issuer("jackieshop.dev")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("customClaim", "customValue")
                .build();

        Payload payload = new Payload(claimSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Error signing token", e);
            throw new RuntimeException(e);
        }
    }
}
