package com.vivace.rental_api.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CheckoutRequest {
    private Long userId;          // Ai là người thuê?
    private Long productItemId;   // Thuê chiếc váy vật lý nào?
    private LocalDate startDate;  // Ngày bắt đầu
    private LocalDate endDate;    // Ngày kết thúc
}