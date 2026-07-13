package com.vivace.rental_api.service;

import com.vivace.rental_api.entity.User;
import com.vivace.rental_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Gọi công cụ BCrypt vào làm việc

    // 1. Hàm xử lý Đăng ký (Register)
    public User registerUser(User newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("Email is already registered!");
        }
        
        // MÃ HÓA MẬT KHẨU trước khi lưu xuống database
        String encodedPassword = passwordEncoder.encode(newUser.getPasswordHash());
        newUser.setPasswordHash(encodedPassword);

        return userRepository.save(newUser);
    }

    // 2. Hàm xử lý Đăng nhập (Login)
    public User loginUser(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // ĐỐI CHIẾU MẬT KHẨU: Dùng hàm matches() thay vì so sánh chuỗi (equals)
            if (passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
                return user; // Mật khẩu khớp -> Đăng nhập thành công
            }
        }
        throw new IllegalArgumentException("Invalid email or password!");
    }
}