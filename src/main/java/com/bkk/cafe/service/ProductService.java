/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 03:40 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.service;

import java.util.List;

import com.bkk.cafe.dto.ProductDto;

public interface ProductService {
	ProductDto createProduct(ProductDto productDto);

	ProductDto getProductByProductId(String productId);

	List<ProductDto> getAllProducts();

	ProductDto updateProduct(String productId, ProductDto productDto);

	void deleteProduct(String productId);
}
