/*
 * @Author : Thant Htoo Aung
 * @Date : 24/08/2024
 * @Time : 12:02 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.service.implement;

import com.bkk.cafe.dto.MenuDto;
import com.bkk.cafe.dto.MenuResponseDto;
import com.bkk.cafe.exception.EntityAlreadyExistsException;
import com.bkk.cafe.exception.EntityNotFoundException;
import com.bkk.cafe.model.Category;
import com.bkk.cafe.model.Menu;
import com.bkk.cafe.model.Product;
import com.bkk.cafe.repository.MenuRepository;
import com.bkk.cafe.service.CategoryService;
import com.bkk.cafe.service.MenuService;
import com.bkk.cafe.service.ProductService;
import com.bkk.cafe.util.DtoUtil;
import com.bkk.cafe.util.EntityUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImplement implements MenuService {
    private final MenuRepository menuRepository;

    private final CategoryService categoryService;

    private final ProductService productService;

    private final ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(MenuServiceImplement.class);
    @Override
    public MenuDto createMenu(MenuDto menuDto) {
        try {
            logger.debug("Attempting to create a new menu with Category ID: {} and Product ID: {}", menuDto.getCategoryId(), menuDto.getProductId());

            logger.debug("Checking if an menu with Category ID '{}' and Product ID '{}' already exists", menuDto.getCategoryId(), menuDto.getProductId());
            if (menuRepository.existsByCategoryIdAndProductId(menuDto.getCategoryId(), menuDto.getProductId())) {
                String errorMessage = "Menu with category ID '" + menuDto.getCategoryId() + "and product Id '" + menuDto.getProductId() + "' already exists";
                logger.error(errorMessage);
                throw new EntityAlreadyExistsException(errorMessage);
            }

            Category category = (Category) categoryService.getCategoryById(menuDto.getCategoryId(), true);
            Product product = (Product) productService.getProductByProductId(menuDto.getProductId(), true);

            Menu menu = new Menu();
            menu.setCategory(category);
            menu.setProduct(product);
            menu.setAvailable(menuDto.getAvailable());
            logger.debug("Saving new menu with Category ID: {} and Product ID: {} to the database", menuDto.getCategoryId(), menuDto.getProductId());
            Menu savedMenu = EntityUtil.saveEntity(menuRepository, menu, "Menu");
            logger.debug("Menu with Category: {} and Product: {} successfully created with ID: {}", savedMenu.getCategory().getName(), savedMenu.getProduct().getName(), savedMenu.getId());

            MenuDto createdMenuDto = new MenuDto();
            createdMenuDto.setCategoryId(savedMenu.getCategory().getId());
            createdMenuDto.setProductId(savedMenu.getProduct().getProductId());
            return createdMenuDto;
        } catch (EntityAlreadyExistsException e) {
            logger.warn("Failed to create menu due to existing Category ID: {} and Product ID: {}", menuDto.getCategoryId(), menuDto.getProductId());
            throw e;
        } catch (EntityNotFoundException e) {
            logger.warn("Failed to create menu due to not found Category ID: {} or Product ID: {}", menuDto.getCategoryId(), menuDto.getProductId());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error creating menu with Category ID: {} and Product ID: {}", menuDto.getCategoryId(), menuDto.getProductId(), e);
            throw new RuntimeException("Unexpected error creating menu: " + e.getMessage());
        }
    }

    @Override
    public void changeAvailabilityStatus(Long categoryId, String productId, Boolean availability) {
        try {
            logger.debug("Attempting to change availability of menu with Category ID: {} and Product ID: {} ", categoryId, productId);

            logger.debug("Checking if an menu with Category ID: '{}' and Product ID: '{}' exists", categoryId, productId);
            Menu menu = menuRepository.findByCategoryIdAndProductId(categoryId, productId).orElseThrow(() -> {
               logger.error("Menu not found with Category ID: {} and Product ID: {}", categoryId, productId);
               return new EntityNotFoundException("Menu with Category ID: " + categoryId + " and Product ID: " + productId);
            });

            logger.debug("Current availability status of menu with Category ID: '{}' and Product ID: '{}' is '{}'", categoryId, productId, menu.getAvailable());
            menu.setAvailable(availability);

            logger.debug("Saving updated availability status for menu with Category ID: '{}' and Product ID: '{}'", categoryId, productId);
            EntityUtil.saveEntity(menuRepository, menu, "Menu");

            logger.info("Successfully changed availability status of menu with Category ID: '{}' and Product ID: '{}' to '{}'", categoryId, productId, availability);
        } catch (EntityNotFoundException e) {
            logger.warn("Failed to change menu availability status due to not found menu with Category ID: {} and Product ID: {} ", categoryId, productId);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error creating menu with Category ID: {} and Product ID: {}", categoryId, productId, e);
            throw new RuntimeException("Unexpected error creating menu: " + e.getMessage());
        }
    }

    @Override
    public MenuResponseDto getMenuById(Long id) {
        logger.debug("Attempting to retrieve menu with ID: {}", id);
        Menu menu = EntityUtil.getEntityById(menuRepository, id, "Menu");
        return DtoUtil.map(menu, MenuResponseDto.class, modelMapper);
    }

    @Override
    public List<MenuResponseDto> getAllMenus() {
        logger.debug("Attempting to retrieve all menus from the database");
        List<Menu> menus = EntityUtil.getAllEntities(menuRepository);
        logger.debug("Successfully retrieved {} menus from the database", menus.size());
        return DtoUtil.mapList(menus, MenuResponseDto.class, modelMapper);
    }

    @Override
    public void updateMenu(Long id, MenuDto menuDto) {

    }

    @Override
    public void deleteMenu(Long id) {

    }
}
