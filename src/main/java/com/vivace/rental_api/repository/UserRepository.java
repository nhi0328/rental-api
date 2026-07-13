package com.vivace.rental_api.repository;

import com.vivace.rental_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Tìm User bằng Email để xử lý logic Login
    // Dùng Optional để tránh lỗi NullPointerException nếu không tìm thấy User
    Optional<User> findByEmail(String email);

    // Kiểm tra xem Email này đã có ai đăng ký chưa (trả về true/false)
    boolean existsByEmail(String email);

    // Kiểm tra xem Số điện thoại đã được sử dụng chưa
    boolean existsByPhoneNumber(String phoneNumber);
}