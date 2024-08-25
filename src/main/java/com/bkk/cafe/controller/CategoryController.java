/*
 * @Author : Thant Htoo Aung
 * @Date : 21/08/2024
 * @Time : 08:08 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bkk.cafe.dto.CategoryDto;
import com.bkk.cafe.service.CategoryService;
import com.bkk.cafe.util.response.ApiResponse;
import com.bkk.cafe.util.response.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "CRUD REST APIs for Category")
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService categoryService;
	private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@PostMapping
	@Operation(summary = "Create a new category", description = "Create a new category. The request body should contain all required fields such as id and name. The response contains the success message and the status of the created category.")
	public ResponseEntity<ApiResponse<String>> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		logger.info("Received request to create category with Name: {}", categoryDto.getName());
		CategoryDto createdCategory = categoryService.createCategory(categoryDto);
		logger.info("Category created successfully with Name: {}", createdCategory.getName());
		return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Category created successfully", "created");
	}
	
	@GetMapping
	@Operation(summary = "Get all categories", description = "Retrieve a list of all categories. The response contains a list of categories with their details, including id and name.")
	public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {
		logger.info("Fetching all categories");
		List<CategoryDto> categories = categoryService.getAllCategories();
		if (categories.isEmpty()) {
			logger.info("Categories fetched successfully. Total categories: {}", 0);
			return ResponseUtil.createSuccessResponse(HttpStatus.OK, "No categories found", new ArrayList<>());
		}
		logger.info("Categories fetched successfully. Total categories: {}", categories.size());
		return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Categories fetched successfully", categories);
	}
}
