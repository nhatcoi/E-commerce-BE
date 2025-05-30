package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
    void updateOrderStatusById(@Param("orderId") Long orderId, @Param("status") String status);

    @Query("""
        SELECT DISTINCT o FROM Order o
            JOIN o.orderDetails oi
            JOIN oi.product p
        WHERE o.user.id = :userId
          AND (:status IS NULL OR :status = 'ALL' OR o.status = :status)
          AND (
            :search IS NULL OR :search = '' OR
            CAST(o.id AS string) LIKE CONCAT('%', :search, '%') OR
            LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
          )
        ORDER BY o.orderDate DESC
    """)
    Page<Order> findAllByStatus(@Param("userId") Long userId,
                                @Param("status") String status,
                                @Param("search") String search,
                                Pageable pageable);
}
