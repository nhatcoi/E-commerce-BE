package com.example.ecommerceweb.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderMetadataIntentDTO {
    private Long orderId;
    private String fullName;
    private String email;
    private String phoneNumber;
}
