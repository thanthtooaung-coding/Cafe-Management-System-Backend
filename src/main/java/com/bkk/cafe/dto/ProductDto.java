/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 03:35 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.dto;

import java.math.BigDecimal;
import java.util.List;

import com.bkk.cafe.util.ProductStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

	private Long id;

	private String productId;

	@NotBlank(message = "Product name is required")
	@Size(max = 100, message = "Product name must be at most 100 characters long")
	private String name;

	@NotBlank(message = "Product description is required")
	@Size(max = 500, message = "Product description must be at most 500 characters long")
	private String description;

	@NotNull(message = "Price is required")
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
	private BigDecimal price;

	@NotNull(message = "Quantity is required")
	@Min(value = 1, message = "Quantity must be at least 1")
	private Integer quantity;

	@NotNull(message = "Status is required")
	private ProductStatus status;

	private Long categoryId;

	private List<ProductVariantDto> variants;
}
