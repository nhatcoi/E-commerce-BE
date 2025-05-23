package com.example.ecommerceweb.dto.order;

import com.example.ecommerceweb.dto.product.ProductOrderRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private List<ProductOrderRequest> products;
    private OrderInfoRequest orderInfo;

    private String discountCode;
    private String paymentMethod;
    private Double totalPrice;

}
