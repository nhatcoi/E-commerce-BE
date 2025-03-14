package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.entity.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.username = ?1 ORDER BY c.product.name ASC")
    List<Cart> findByUserName(String username);

    @Query("SELECT SUM(c.quantity) FROM Cart c WHERE c.user.username = ?1")
    Integer countByUsername(String username);

    @Query("SELECT c FROM Cart c WHERE c.user.username = ?1 AND c.product.id = ?2")
    Optional<Cart> findByUsernameAndProductId(String username, Long productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.user.id = :userId AND c.product.id IN :productIds")
    void deleteByUserIdAndProductId(@Param("userId") Long userId, @Param("productIds") List<Long> productIds);
}
