package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.user.username = ?1 ORDER BY c.product.name ASC")
    List<Cart> findByUserName(String username);

    @Query("SELECT COUNT(c) FROM Cart c WHERE c.user.username = ?1")
    Integer countByUsername(String username);
}
