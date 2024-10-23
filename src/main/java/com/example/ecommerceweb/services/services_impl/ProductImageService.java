package com.example.ecommerceweb.services.services_impl;

import com.example.ecommerceweb.entities.Product;
import com.example.ecommerceweb.entities.ProductImage;
import com.example.ecommerceweb.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

    public void saveProductImage(Product product, String imageUrl) {
        ProductImage newProductImage = ProductImage.builder()
                .product(product)
                .imageUrl(imageUrl)
                .build();
        productImageRepository.save(newProductImage);
    }
}