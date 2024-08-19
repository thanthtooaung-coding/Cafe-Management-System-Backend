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

	ProductDto getProductById(Long id);

	List<ProductDto> getAllProducts();

	ProductDto updateProduct(Long id, ProductDto productDto);

	void deleteProduct(Long id);
}
