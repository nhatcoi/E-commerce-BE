package com.example.ecommerceweb.service;

import com.example.ecommerceweb.dto.request.order.OrderRequest;
import com.example.ecommerceweb.dto.response.cart.OrderMetadataIntentDTO;
import com.example.ecommerceweb.enums.StatusEnum;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderMetadataIntentDTO createOrder(OrderRequest orderRequest);
    void updateOrderStatus(Long orderId, StatusEnum statusEnum);

    Integer countOrders(Long userId);
}
