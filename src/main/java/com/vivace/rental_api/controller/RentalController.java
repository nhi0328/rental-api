package com.vivace.rental_api.controller;

import com.vivace.rental_api.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin(origins = "*") // Mở khóa CORS để các bạn Frontend gọi API không bị chặn
public class RentalController {

    @Autowired
    private RentalService rentalService;

    /**
     * API 1: Kiểm tra xem váy có trống lịch không
     * URL test: /api/rentals/check-availability?productItemId=1&startDate=2026-07-15&endDate=2026-07-20
     */
    @GetMapping("/check-availability")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam Long productItemId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        
        boolean isAvailable = rentalService.isItemAvailableForDates(productItemId, startDate, endDate);
        return ResponseEntity.ok(isAvailable);
    }

    /**
     * API 2: Tính toán chi tiết cấu trúc giá
     * URL test: /api/rentals/calculate-cost?productItemId=1&startDate=2026-07-15&endDate=2026-07-20
     */
    @GetMapping("/calculate-cost")
    public ResponseEntity<Map<String, Object>> calculateCost(
            @RequestParam Long productItemId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        
        Map<String, Object> costDetails = rentalService.calculateRentalCost(productItemId, startDate, endDate);
        return ResponseEntity.ok(costDetails);
    }
}