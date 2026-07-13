package com.vivace.rental_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    // Nhiều dòng chi tiết thuộc về 1 Đơn hàng tổng
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Mỗi dòng chi tiết chỉ định rõ 1 chiếc váy vật lý cụ thể trong kho
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id", nullable = false)
    private ProductItem productItem;

    @Column(name = "order_type", nullable = false, length = 10)
    private String orderType; // "Sale" (Mua) hoặc "Rent" (Thuê)

    @Column(name = "price", nullable = false)
    private BigDecimal price; // Giá tại thời điểm giao dịch
}