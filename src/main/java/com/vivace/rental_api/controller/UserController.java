package com.vivace.rental_api.controller;

import com.vivace.rental_api.entity.User;
import com.vivace.rental_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * API 1: Đăng ký tài khoản
     * Phương thức: POST
     * Endpoint: /api/users/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            // Giấu mật khẩu đi trước khi trả dữ liệu báo thành công về cho Frontend
            savedUser.setPasswordHash("[PROTECTED]"); 
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * API 2: Đăng nhập
     * Phương thức: POST
     * Endpoint: /api/users/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        try {
            User loggedInUser = userService.loginUser(email, password);
            loggedInUser.setPasswordHash("[PROTECTED]");
            return ResponseEntity.ok(loggedInUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}