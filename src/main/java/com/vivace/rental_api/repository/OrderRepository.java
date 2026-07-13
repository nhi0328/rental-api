package com.vivace.rental_api.repository;

import com.vivace.rental_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Tìm tất cả các đơn hàng của một khách hàng cụ thể
    List<Order> findByUserId(Long userId);
}