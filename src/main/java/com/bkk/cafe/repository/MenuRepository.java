/*
 * @Author : Thant Htoo Aung
 * @Date : 24/08/2024
 * @Time : 08:58 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.repository;

import com.bkk.cafe.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query(value = "SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END " +
            "FROM menu m " +
            "INNER JOIN product p ON m.product_id = p.id " +
            "WHERE m.category_id = :categoryId AND p.product_id = :productId", nativeQuery = true)
    boolean existsByCategoryIdAndProductId(Long categoryId, String productId);

    @Query("SELECT m FROM Menu m JOIN m.product p WHERE m.category.id = :categoryId AND p.productId = :productId")
    Optional<Menu> findByCategoryIdAndProductId(
            @Param("categoryId") Long categoryId,
            @Param("productId") String productId
    );
}
