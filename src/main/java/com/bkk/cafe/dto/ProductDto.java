/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 03:35 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.dto;

import java.math.BigDecimal;

import com.bkk.cafe.util.ProductStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
	
	private Long id;
	
	@NotBlank(message = "Product ID is required")
	@Size(max = 20, message = "Product ID must be at most 20 characters long")
	private String productId;
	
	@NotBlank(message = "Product name is required")
	@Size(max = 100, message = "Product name must be at most 100 characters long")
	private String name;
	
	@NotBlank(message = "Product description is required")
	@Size(max = 500, message = "Product description must be at most 500 characters long")
	private String description;
	
	@NotBlank(message = "Price is required")
	private BigDecimal price;

	@NotBlank(message = "Quantity is required")
	private Integer quantity;
	
	@NotBlank(message = "Status is required")
	private ProductStatus status;
}
