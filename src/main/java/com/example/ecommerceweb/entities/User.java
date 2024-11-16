package com.example.ecommerceweb.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "facebook_id")
    private String facebookId;

    @Column(name = "google_id")
    private String googleId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
