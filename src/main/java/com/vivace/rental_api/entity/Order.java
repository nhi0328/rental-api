package com.vivace.rental_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    // Liên kết Đơn hàng với Khách hàng: Nhiều đơn hàng có thể thuộc về 1 User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "order_date", updatable = false)
    private LocalDateTime orderDate;

    @Column(name = "total_product_amount", nullable = false)
    private BigDecimal totalProductAmount;

    @Column(name = "total_deposit_amount")
    private BigDecimal totalDepositAmount;

    @Column(name = "final_total", nullable = false)
    private BigDecimal finalTotal;

    @Column(name = "payment_status", length = 30)
    private String paymentStatus = "Unpaid"; // Unpaid, Paid, Partially_Paid

    @Column(name = "order_status", length = 30)
    private String orderStatus = "Pending"; // Pending, Processing, Completed, Cancelled

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
    }
}