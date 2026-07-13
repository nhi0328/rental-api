package com.vivace.rental_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash; // Mật khẩu đã được mã hóa

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "role", length = 20)
    private String role = "Customer"; // Các quyền: Customer, Staff, Admin

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Tự động gán thời gian hiện tại khi một User mới được tạo ra
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}