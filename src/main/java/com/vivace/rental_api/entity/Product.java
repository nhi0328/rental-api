package com.vivace.rental_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data // Tự động tạo Getter, Setter, toString (của thư viện Lombok)
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", nullable = false, length = 200)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "base_sale_price")
    private BigDecimal baseSalePrice;

    @Column(name = "base_rent_price")
    private BigDecimal baseRentPrice;

    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;

    @Column(name = "image_url", length = 500)
    private String imageUrl;
}