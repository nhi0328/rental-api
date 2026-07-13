package com.vivace.rental_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // --- CÁC THÔNG SỐ ĐO LƯỜNG CHI TIẾT (Tính bằng cm/kg) ---

    @Column(name = "dress_length")
    private Integer dressLength; // Chiều dài váy (cm)

    @Column(name = "bust")
    private Integer bust; // Vòng 1 - Ngực (cm)

    @Column(name = "waist")
    private Integer waist; // Vòng 2 - Eo (cm)

    @Column(name = "hips")
    private Integer hips; // Vòng 3 - Mông (cm)

    @Column(name = "suitable_height", length = 50)
    private String suitableHeight; // Chiều cao phù hợp. VD: "155 - 165"

    @Column(name = "suitable_weight", length = 50)
    private String suitableWeight; // Cân nặng phù hợp. VD: "45 - 50"

    @Column(name = "shoulder_width")
    private Integer shoulderWidth; // Rộng vai (cm)

    @Column(name = "bicep")
    private Integer bicep; // Vòng bắp tay (cm)

    @Column(name = "sleeve_length")
    private Integer sleeveLength; // Chiều dài tay áo (cm)

    // ---------------------------------------------------------

    @Column(name = "color", length = 30)
    private String color;

    @Column(name = "barcode", nullable = false, unique = true, length = 50)
    private String barcode;

    @Column(name = "item_status", length = 30)
    private String itemStatus; // Available, Rented, Maintenance, Sold
}