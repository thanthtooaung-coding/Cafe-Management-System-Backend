/*
 * @Author : Thant Htoo Aung
 * @Date : 20/08/2024
 * @Time : 09:10 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.service.implement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkk.cafe.dto.ProductDto;
import com.bkk.cafe.exception.EntityAlreadyExistsException;
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
public class ProductServiceImplement implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	private final Logger logger = LoggerFactory.getLogger(ProductServiceImplement.class);

	@Override
	@Transactional
	public ProductDto createProduct(ProductDto productDto) {
		if (productRepository.existsByName(productDto.getName())) {
			String errorMessage = "Product with name '" + productDto.getName() + "' already exists";
			logger.error(errorMessage);
			throw new EntityAlreadyExistsException(errorMessage);
		}
		Category category = EntityUtil.getEntityById(categoryRepository, productDto.getCategoryId(), "Category");
		Product product = DtoUtil.map(productDto, Product.class, modelMapper);
		product.setCategory(category);
		product.setProductId(generateProductId(product.getCategory()));
		Product savedProduct = EntityUtil.saveEntity(productRepository, product, "Product");
		if (productDto.getVariants() != null) {
			List<ProductVariant> variants = productDto.getVariants().stream().map(variantDto -> {
				ProductVariant variant = DtoUtil.map(variantDto, ProductVariant.class, modelMapper);
				variant.setProduct(savedProduct);
				return variant;
			}).collect(Collectors.toList());
//			productVariantRepository.saveAll(variants);
			savedProduct.getVariants().addAll(variants);
			productRepository.save(savedProduct);
		}
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
	public ProductDto getProductByProductId(String productId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getProductById'");
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
