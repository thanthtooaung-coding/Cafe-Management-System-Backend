/*
 * @Author : Thant Htoo Aung
 * @Date : 24/08/2024
 * @Time : 11:15 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.controller;

import com.bkk.cafe.dto.MenuDto;
import com.bkk.cafe.dto.MenuResponseDto;
import com.bkk.cafe.service.MenuService;
import com.bkk.cafe.util.response.ApiResponse;
import com.bkk.cafe.util.response.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CRUD REST APIs for Menu")
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @PostMapping
    @Operation(summary = "Create a new menu", description = "Create a new menu. The request body should contain all required fields such as categoryId, productId, and available. The response contains a success message and the status of the created menu.")
    public ResponseEntity<ApiResponse<String>> createMenu(@Valid @RequestBody MenuDto menuDto) {
        logger.info("Received request to create menu with Category ID: {} and Product ID: {}", menuDto.getCategoryId(), menuDto.getProductId());
        MenuDto createdMenu = menuService.createMenu(menuDto);
        logger.info("Menu created successfully with ID: {}", createdMenu.getId());
        return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Menu created successfully", "created");
    }

    @PostMapping("/change-availability")
    @Operation(summary = "Change menu availability status", description = "Change the availability status of a menu by its ID. The request should include the menu ID and the new availability status. The response contains a success message and the updated status.")
    public ResponseEntity<ApiResponse<String>> changeAvailabilityStatus(
            @RequestParam Long categoryId,
            @RequestParam String productId,
            @RequestParam Boolean availability) {

        logger.info("Received request to change availability status of menu with Category ID: {} and Product ID: {} to '{}'", categoryId, productId, availability);
        menuService.changeAvailabilityStatus(categoryId, productId, availability);
        logger.info("Successfully changed availability status of menu with Category ID: {} and Product ID: {} to '{}'", categoryId, productId, availability);

        return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Menu availability status updated successfully", "updated");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get menu by ID", description = "Retrieve a menu's details by its ID. The response contains the menu's information, including id, category, product, and availability status.")
    public ResponseEntity<ApiResponse<MenuResponseDto>> getMenuById(@PathVariable Long id) {
        logger.info("Received request to retrieve menu with ID: {}", id);
        MenuResponseDto menuDto = menuService.getMenuById(id);
        logger.info("Successfully retrieved menu with ID: {}", id);
        return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Menu retrieved successfully", menuDto);
    }

    @GetMapping
    @Operation(summary = "Get all menus", description = "Retrieve a list of all menus. The response contains a list of menus with their details, including id, category, product, and availability status.")
    public ResponseEntity<ApiResponse<List<MenuResponseDto>>> getAllMenus() {
        logger.info("Fetching all menus");
        List<MenuResponseDto> menus = menuService.getAllMenus();
        logger.info("Menus fetched successfully. Total menus: {}", menus.size());
        return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Menus fetched successfully", menus);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing menu", description = "Update an existing menu by its ID. The request body should contain all required fields such as categoryId, productId, and available status. The response contains a success message and the updated status.")
    public ResponseEntity<ApiResponse<String>> updateMenu(@PathVariable Long id,
                                                          @Valid @RequestBody MenuDto menuDto) {
        logger.info("Received request to update menu with ID: {}", id);
        menuService.updateMenu(id, menuDto);
        logger.info("Menu with ID: {} updated successfully", id);
        return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Menu updated successfully", "updated");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a menu", description = "Delete a menu by its ID. The response contains a success message indicating that the menu was successfully deleted.")
    public ResponseEntity<ApiResponse<String>> deleteMenu(@PathVariable Long id) {
        logger.info("Received request to delete menu with ID: {}", id);
        menuService.deleteMenu(id);
        logger.info("Menu with ID: {} deleted successfully", id);
        return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Menu deleted successfully", "deleted");
    }
}
