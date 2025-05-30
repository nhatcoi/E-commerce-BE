package com.example.ecommerceweb.filter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductFilter {
    private Boolean sortByNew;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean inStock;
    private Integer category;
    private String search;
    private String sortBy;
    private String status;
}