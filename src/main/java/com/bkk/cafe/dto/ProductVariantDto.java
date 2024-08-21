/*
 * @Author : Thant Htoo Aung
 * @Date : 21/08/2024
 * @Time : 12:47 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantDto {
	
	private Long id;
	
	@NotBlank(message = "Product variant name is required")
	@Size(max = 50, message = "Product variant name must be at most 50 characters long")
	private String variantName;
	
	@NotNull(message = "Product variant price is required")
	@DecimalMin(value = "0.0", inclusive = false, message = "Product variant price must be greater than zero")
	private BigDecimal price;
	
	@NotNull(message = "Quantity is required")
	@Min(value = 1, message = "Quantity must be at least 1")
	private Integer quantity;
}
