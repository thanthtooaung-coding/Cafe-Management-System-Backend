/*
 * @Author : Thant Htoo Aung
 * @Date : 20/08/2024
 * @Time : 09:10 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.service.implement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.bkk.cafe.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkk.cafe.dto.ProductDto;
import com.bkk.cafe.exception.EntityAlreadyExistsException;
import com.bkk.cafe.exception.EntityNotFoundException;
import com.bkk.cafe.model.Category;
import com.bkk.cafe.model.Product;
import com.bkk.cafe.model.ProductVariant;
import com.bkk.cafe.repository.CategoryRepository;
import com.bkk.cafe.repository.ProductRepository;
import com.bkk.cafe.service.ProductService;
import com.bkk.cafe.util.DtoUtil;
import com.bkk.cafe.util.EntityUtil;

import jakarta.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImplement implements ProductService {
	private final ProductRepository productRepository;

	private final CategoryService categoryService;

	private final ModelMapper modelMapper;

	private final Logger logger = LoggerFactory.getLogger(ProductServiceImplement.class);

	@Override
	@Transactional
	public ProductDto createProduct(ProductDto productDto) {
		logger.debug("Attempting to create a new product with Product Name: {}", productDto.getName());

		logger.debug("Checking if an product with name '{}' already exists", productDto.getName());
		if (productRepository.existsByName(productDto.getName())) {
			String errorMessage = "Product with name '" + productDto.getName() + "' already exists";
			logger.error(errorMessage);
			throw new EntityAlreadyExistsException(errorMessage);
		}

		Category category = (Category) categoryService.getCategoryById(productDto.getCategoryId(), false);

		Product product = DtoUtil.map(productDto, Product.class, modelMapper);
		product.setCategory(category);
		product.setProductId(generateProductId(product.getCategory()));
		logger.debug("Saving new product with Product ID: {} to the database", productDto.getProductId());

		if (productDto.getVariants() != null) {
			List<ProductVariant> variants = productDto.getVariants().stream().map(variantDto -> {
				ProductVariant variant = DtoUtil.map(variantDto, ProductVariant.class, modelMapper);
				variant.setProduct(product);
				return variant;
			}).toList();
			product.setVariants(new HashSet<>(variants));
		}

		Product savedProduct = EntityUtil.saveEntity(productRepository, product, "Product");
		logger.debug("Product with Product ID: {} successfully created with ID: {}", savedProduct.getProductId(), savedProduct.getId());
		return DtoUtil.map(savedProduct, ProductDto.class, modelMapper);
	}

	private String generateProductId(Category category) {
		String categoryName = category.getName().toUpperCase().replaceAll("\\s+", "");
		if (categoryName.length() > 5) {
			categoryName = categoryName.substring(0, 5);
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String datePart = LocalDateTime.now().format(formatter);
		String uniquePart = UUID.randomUUID().toString().substring(0, 4).toUpperCase();

		return categoryName + "-" + datePart + "-" + uniquePart;
	}

	@Override
	public Object getProductByProductId(String productId, boolean fromMenuService) {
		logger.debug("Attempting to retrieve product with Product ID: {}", productId);

		Product product = productRepository.findProductByProductId(productId).orElseThrow(() -> {
			logger.error("Product not found with Product ID: {}", productId);
			return new EntityNotFoundException("Product not found with Product ID: " + productId);
		});
		String successMessage = "Successfully retrieved product with Product ID: " + productId;
		if(fromMenuService) {
			logger.debug(successMessage);
			return product;
		}

		ProductDto productDto = DtoUtil.map(product, ProductDto.class, modelMapper);
		productDto.setId(null);

		logger.debug(successMessage);
		return productDto;
	}

	@Override
	public List<ProductDto> getAllProducts() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getAllProducts'");
	}

	@Override
	public ProductDto updateProduct(String productId, ProductDto productDto) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
	}

	@Override
	public void deleteProduct(String productId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteProduct'");
	}

}
