package com.vivace.rental_api.service;

import com.vivace.rental_api.dto.CheckoutRequest;
import com.vivace.rental_api.entity.*;
import com.vivace.rental_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class OrderService {

    @Autowired private UserRepository userRepository;
    @Autowired private ProductItemRepository productItemRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderDetailRepository orderDetailRepository;
    @Autowired private RentalRepository rentalRepository;
    @Autowired private RentalService rentalService; // Gọi người quản lý lịch thuê sang hỗ trợ

    // Dùng @Transactional để nếu có lỗi ở bất kỳ bước nào, toàn bộ giao dịch sẽ bị hủy (Rollback), không bị rác dữ liệu
    @Transactional 
    public Order createRentalOrder(CheckoutRequest request) {
        
        // 1. Kiểm tra xem chiếc váy này có trống lịch không
        boolean isAvailable = rentalService.isItemAvailableForDates(
                request.getProductItemId(), request.getStartDate(), request.getEndDate());
        if (!isAvailable) {
            throw new RuntimeException("Sorry, this item is already rented for the selected dates.");
        }

        // 2. Tính toán tiền nong
        Map<String, Object> costs = rentalService.calculateRentalCost(
                request.getProductItemId(), request.getStartDate(), request.getEndDate());
        BigDecimal finalTotal = (BigDecimal) costs.get("finalTotalToPay");
        BigDecimal deposit = (BigDecimal) costs.get("depositAmount");
        BigDecimal totalRent = (BigDecimal) costs.get("totalRentCost");

        // 3. Lấy thông tin User và Váy từ DB
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found!"));
        ProductItem item = productItemRepository.findById(request.getProductItemId())
                .orElseThrow(() -> new RuntimeException("Product item not found!"));

        // 4. BƯỚC LƯU DATABASE: Tạo Đơn hàng gốc (Order)
        Order order = new Order();
        order.setUser(user);
        order.setTotalProductAmount(totalRent);
        order.setTotalDepositAmount(deposit);
        order.setFinalTotal(finalTotal);
        order = orderRepository.save(order);

        // 5. BƯỚC LƯU DATABASE: Tạo Chi tiết đơn (OrderDetail)
        OrderDetail detail = new OrderDetail();
        detail.setOrder(order);
        detail.setProductItem(item);
        detail.setOrderType("Rent");
        detail.setPrice(totalRent);
        detail = orderDetailRepository.save(detail);

        // 6. BƯỚC LƯU DATABASE: Tạo Lịch thuê (Rental)
        Rental rental = new Rental();
        rental.setOrderDetail(detail);
        rental.setStartDate(request.getStartDate());
        rental.setEndDate(request.getEndDate());
        rental.setRentalStatus("Waiting");
        rentalRepository.save(rental);

        return order;
    }
}