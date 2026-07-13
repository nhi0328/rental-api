package com.vivace.rental_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rentals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_id", nullable = false, unique = true)
    private OrderDetail orderDetail;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "actual_return_date")
    private LocalDate actualReturnDate;

    @Column(name = "late_fee")
    private BigDecimal lateFee;

    @Column(name = "damage_fee")
    private BigDecimal damageFee;

    @Column(name = "rental_status", length = 30)
    private String rentalStatus; // Waiting, Active, Returned, Overdue
}