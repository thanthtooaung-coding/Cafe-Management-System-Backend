/*
 * @Author : Thant Htoo Aung
 * @Date : 24/08/2024
 * @Time : 11:57 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.service.implement;

import com.bkk.cafe.dto.CategoryDto;
import com.bkk.cafe.exception.EntityAlreadyExistsException;
import com.bkk.cafe.model.Category;
import com.bkk.cafe.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceImplementTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryServiceImplement categoryServiceImplement;

    @SuppressWarnings("resource")
    public CategoryServiceImplementTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategorySuccess() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Beverages");

        Category category = new Category();
        category.setName("Beverages");

        when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(false);
        when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        CategoryDto result = categoryServiceImplement.createCategory(categoryDto);

        assertNotNull(result);
        assertEquals(categoryDto.getName(), result.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testCreateCategoryAlreadyExists() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Beverages");

        when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(true);

        Exception exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            categoryServiceImplement.createCategory(categoryDto);
        });

        assertEquals("Category with name 'Beverages' already exists", exception.getMessage());
    }

    @Test
    public void testGetAllCategories() {
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setName("Beverages");
        categories.add(category);

        List<CategoryDto> categoryDtos = new ArrayList<>();
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Beverages");
        categoryDtos.add(categoryDto);

        when(categoryRepository.findAll()).thenReturn(categories);
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        List<CategoryDto> result = categoryServiceImplement.getAllCategories();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Beverages", result.get(0).getName());
    }
}

