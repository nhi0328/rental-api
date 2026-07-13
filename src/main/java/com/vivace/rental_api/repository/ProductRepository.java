package com.vivace.rental_api.repository;

import com.vivace.rental_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Không cần viết lệnh SQL nào ở đây cả!
    // Thư viện JpaRepository đã tự động viết sẵn các lệnh tìm kiếm, thêm, sửa, xóa cho bảng Product rồi.
}