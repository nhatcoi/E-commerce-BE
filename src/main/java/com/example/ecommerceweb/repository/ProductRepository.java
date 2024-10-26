package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN FETCH p.categoryId ORDER BY p.createdAt DESC")
    List<Product> fetchLatestProducts(Pageable pageable);

    @Query("SELECT p FROM Product p JOIN FETCH p.categoryId WHERE p.price BETWEEN :min AND :max")
    List<Product> findByPriceBetween(double min, double max);
}
