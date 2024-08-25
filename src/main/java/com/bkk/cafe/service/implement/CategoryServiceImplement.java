/*
 * @Author : Thant Htoo Aung
 * @Date : 21/08/2024
 * @Time : 08:12 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.service.implement;

import java.util.List;

import com.bkk.cafe.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkk.cafe.dto.CategoryDto;
import com.bkk.cafe.exception.EntityAlreadyExistsException;
import com.bkk.cafe.model.Category;
import com.bkk.cafe.repository.CategoryRepository;
import com.bkk.cafe.service.CategoryService;
import com.bkk.cafe.util.DtoUtil;
import com.bkk.cafe.util.EntityUtil;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplement implements CategoryService {
	private final CategoryRepository categoryRepository;

	private final ModelMapper modelMapper;
	
	private final Logger logger = LoggerFactory.getLogger(CategoryServiceImplement.class);
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		try {
			logger.debug("Attempting to create a new category with Name: {}", categoryDto.getName());

			logger.debug("Checking if an category with name '{}' already exists", categoryDto.getName());
			if (categoryRepository.existsByName(categoryDto.getName())) {
				String errorMessage = "Category with name '" + categoryDto.getName() + "' already exists";
				logger.error(errorMessage);
				throw new EntityAlreadyExistsException(errorMessage);
			}
			Category category = DtoUtil.map(categoryDto, Category.class, modelMapper);

			logger.debug("Saving new category with Name: {} to the database", categoryDto.getName());
			Category savedCategory = EntityUtil.saveEntity(categoryRepository, category, "Category");
			logger.debug("Category with Name: {} successfully created with ID: {}", savedCategory.getName(), savedCategory.getId());
			return DtoUtil.map(savedCategory, CategoryDto.class, modelMapper);
		} catch (EntityAlreadyExistsException e) {
			throw e;
		}  catch (Exception e) {
			logger.error("Unexpected error creating category", e);
			throw new RuntimeException("Unexpected error creating category: " + e.getMessage());
		}
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		logger.debug("Attempting to retrieve all categories from the database");
		List<Category> categories = EntityUtil.getAllEntities(categoryRepository);
		logger.debug("Successfully retrieved {} categories from the database", categories.size());
//		List<CategoryDto> categoryDtos = DtoUtil.mapList(categories, CategoryDto.class, modelMapper);
//		categoryDtos.forEach(dto -> dto.setId(null));
		return DtoUtil.mapList(categories, CategoryDto.class, modelMapper);
	}

	@Override
	public Object getCategoryById(Long id, boolean fromMenuService) {
		logger.debug("Attempting to retrieve category with ID: {}", id);
		Category category = EntityUtil.getEntityById(categoryRepository, id, "Category");
		logger.debug("Successfully retrieved category with ID: {}", id);
		if (fromMenuService) {
			return category;
		}
		return DtoUtil.map(category, CategoryDto.class, modelMapper);
	}
}
