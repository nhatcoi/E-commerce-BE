package com.example.ecommerceweb.dto.request.order;

import com.example.ecommerceweb.dto.request.product.ProductOrderRequest;
import com.example.ecommerceweb.dto.response.cart.OrderMetadataIntentDTO;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutRequest {
    private List<ProductOrderRequest> products;
    private OrderMetadataIntentDTO orderMetadata;
}
