/*
 * @Author : Thant Htoo Aung
 * @Date : 24/08/2024
 * @Time : 12:00 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.service;

import com.bkk.cafe.dto.MenuDto;
import com.bkk.cafe.dto.MenuResponseDto;

import java.util.List;

public interface MenuService {
    MenuDto createMenu(MenuDto menuDto);
    void changeAvailabilityStatus(Long categoryId, String productId, Boolean availability);

    MenuResponseDto getMenuById(Long id);

    List<MenuResponseDto> getAllMenus();

    void updateMenu(Long id, MenuDto menuDto);

    void deleteMenu(Long id);
}
