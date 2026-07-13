package com.vivace.rental_api.repository;

import com.vivace.rental_api.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
    
    // Tìm tất cả các chiếc váy vật lý thuộc về một mẫu váy cụ thể
    List<ProductItem> findByProductId(Long productId);

    // CỰC KỲ HỮU ÍCH: Tìm váy dựa trên số đo vòng eo tối đa của khách
    // Spring Boot sẽ tự tạo lệnh SQL: SELECT * FROM product_items WHERE waist <= ? AND item_status = ?
    List<ProductItem> findByWaistLessThanEqualAndItemStatus(Integer maxWaist, String itemStatus);
    
    // Tìm váy trống lịch theo khoảng chiều dài (VD: Khách lùn không muốn mặc váy quá dài)
    List<ProductItem> findByDressLengthBetweenAndItemStatus(Integer minLength, Integer maxLength, String itemStatus);
}