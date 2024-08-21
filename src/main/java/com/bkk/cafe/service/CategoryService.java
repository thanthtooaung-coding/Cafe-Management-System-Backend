/*
 * @Author : Thant Htoo Aung
 * @Date : 21/08/2024
 * @Time : 08:11 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.service;

import java.util.List;

import com.bkk.cafe.dto.CategoryDto;

public interface CategoryService {
	CategoryDto createCategory(CategoryDto categoryDto);
	List<CategoryDto> getAllCategories();
}
