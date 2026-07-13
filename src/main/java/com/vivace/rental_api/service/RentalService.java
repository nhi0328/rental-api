package com.vivace.rental_api.service;

import com.vivace.rental_api.entity.Product;
import com.vivace.rental_api.entity.ProductItem;
import com.vivace.rental_api.repository.ProductItemRepository;
import com.vivace.rental_api.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private ProductItemRepository productItemRepository; // Gọi thêm Thủ kho váy

    // 1. Hàm kiểm tra trùng lịch (Đã viết ở bước trước)
    public boolean isItemAvailableForDates(Long productItemId, LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date!");
        }
        int overlappingCount = rentalRepository.countOverlappingRentals(productItemId, startDate, endDate);
        return overlappingCount == 0;
    }

    // 2. Hàm tính toán chi phí thuê để trả về cho Frontend hiển thị
    @Transactional(readOnly = true)
    public Map<String, Object> calculateRentalCost(Long productItemId, LocalDate startDate, LocalDate endDate) {
        
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date!");
        }

        // Lấy thông tin chiếc váy vật lý cụ thể đang được chọn
        ProductItem item = productItemRepository.findById(productItemId)
                .orElseThrow(() -> new RuntimeException("Product item not found in inventory!"));
        
        // Từ chiếc váy vật lý, truy xuất ngược ra mẫu thiết kế (Product) để lấy bảng giá
        Product product = item.getProduct();

        // Tính số ngày thuê (Sử dụng thư viện ChronoUnit của Java)
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        
        // Nếu khách thuê và trả trong cùng 1 ngày, hệ thống vẫn tính phí tối thiểu là 1 ngày
        if (days == 0) {
            days = 1; 
        }

        // Lấy giá trị từ Database
        BigDecimal rentPricePerDay = product.getBaseRentPrice();
        BigDecimal deposit = product.getDepositAmount();

        // Thực hiện phép tính: Tổng Tiền Thuê = Giá thuê 1 ngày * Số ngày
        BigDecimal totalRent = rentPricePerDay.multiply(BigDecimal.valueOf(days));
        
        // Tổng Khách Cần Trả (Hoặc Đặt Cọc Trước) = Tổng Tiền Thuê + Tiền Cọc
        BigDecimal finalTotal = totalRent.add(deposit);

        // Đóng gói toàn bộ dữ liệu thành 1 Object JSON để ném ra cho Frontend hiển thị
        Map<String, Object> costDetails = new HashMap<>();
        costDetails.put("rentalDays", days);
        costDetails.put("rentPricePerDay", rentPricePerDay);
        costDetails.put("totalRentCost", totalRent);
        costDetails.put("depositAmount", deposit);
        costDetails.put("finalTotalToPay", finalTotal);

        return costDetails;
    }
}