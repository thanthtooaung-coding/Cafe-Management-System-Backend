/*
 * @Author : Thant Htoo Aung
 * @Date : 21/08/2024
 * @Time : 08:09 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
	private Long id;
	
	@NotBlank(message = "Category name is required")
	@Size(max = 100, message = "Category name must be at most 100 characters long")
	private String name;
}
