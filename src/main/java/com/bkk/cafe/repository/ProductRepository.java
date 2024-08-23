/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 12:21 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bkk.cafe.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	boolean existsByName(String name);

	Optional<Product> findProductByProductId(String productId);

}
