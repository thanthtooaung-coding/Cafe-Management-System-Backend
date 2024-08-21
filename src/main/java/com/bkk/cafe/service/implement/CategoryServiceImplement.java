/*
 * @Author : Thant Htoo Aung
 * @Date : 21/08/2024
 * @Time : 08:12 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.service.implement;

import java.util.List;

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
public class CategoryServiceImplement implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private final Logger logger = LoggerFactory.getLogger(CategoryServiceImplement.class);
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		try {
			if (categoryRepository.existsByName(categoryDto.getName())) {
				String errorMessage = "Category with name '" + categoryDto.getName() + "' already exists";
				logger.error(errorMessage);
				throw new EntityAlreadyExistsException(errorMessage);
			}
			Category category = DtoUtil.map(categoryDto, Category.class, modelMapper);
			Category savedCategory = EntityUtil.saveEntity(categoryRepository, category, "Category");
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
		List<Category> categorys = EntityUtil.getAllEntities(categoryRepository);
		return DtoUtil.mapList(categorys, CategoryDto.class, modelMapper);
	}
}
