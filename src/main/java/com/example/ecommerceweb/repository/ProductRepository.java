package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p ORDER BY p.createdAt DESC")
    List<Product> fetchLatestProducts(Pageable pageable);

    List<Product> findByPriceBetween(double min, double max);
}
