package com.vivace.rental_api.repository;

import com.vivace.rental_api.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    // Thuật toán kiểm tra giao nhau giữa 2 khoảng thời gian:
    // (Ngày Bắt Đầu 1 <= Ngày Kết Thúc 2) VÀ (Ngày Kết Thúc 1 >= Ngày Bắt Đầu 2)
    @Query("SELECT COUNT(r) FROM Rental r WHERE r.orderDetail.productItem.id = :productItemId " +
           "AND r.rentalStatus IN ('Waiting', 'Active') " +
           "AND r.startDate <= :endDate AND r.endDate >= :startDate")
    int countOverlappingRentals(@Param("productItemId") Long productItemId,
                                @Param("startDate") LocalDate startDate,
                                @Param("endDate") LocalDate endDate);
}