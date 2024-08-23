/*
 * @Author : Thant Htoo Aung
 * @Date : 20/08/2024
 * @Time : 09:00 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bkk.cafe.dto.ProductDto;
import com.bkk.cafe.service.ProductService;
import com.bkk.cafe.util.response.ApiResponse;
import com.bkk.cafe.util.response.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "CRUD REST APIs for Product")
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping
	@Operation(summary = "Create a new product", description = "Create a new product. The request body should contain all required fields such as productId, name, description, price, quantity, and status. The response contains the success message and the status of the created product.")
	public ResponseEntity<ApiResponse<String>> createProduct(@Valid @RequestBody ProductDto productDto) {
		logger.info("Creating product with Product Id: {}", productDto.getProductId());
		ProductDto createdProduct = productService.createProduct(productDto);
		logger.info("Product created with ID: {}", createdProduct.getId());
		return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Product created successfully", "created");
	}

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by Product ID", description = "Retrieve an product's details by their Product ID. The response contains the product's information, including id, productId, name, description, price, quantity, and status.")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable String productId) {
		logger.info("Fetching product with Product ID: {}", productId);
        ProductDto product = productService.getProductByProductId(productId);
        logger.info("Product with Product ID: {} fetched successfully", productId);
		return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Product fetched successfully", product);
	}

    @PutMapping("/{productId}")
    @Operation(summary = "Update an existing product", description = "Update an existing product. The response is updated Product object with id, productId, name, description, price, quantity, and status.")
    public ResponseEntity<ApiResponse<String>> updateProduct(@PathVariable String productId,
			@Valid @RequestBody ProductDto productDto) {
		logger.info("Updating product with Product ID: {}", productId);
		productService.updateProduct(productId, productDto);
		return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Product updated successfully", "updated");
	}

    @DeleteMapping("/{productId}")
	@Operation(summary = "Delete an product", description = "Delete an product by their Product ID. The response contains a success message indicating that the product was successfully deleted.")
	public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable String productId) {
		logger.info("Deleting product with product ID: {}", productId);
		productService.deleteProduct(productId);
		return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Product deleted successfully", "deleted");
	}
}
