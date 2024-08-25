/*
 * @Author : Thant Htoo Aung
 * @Date : 25/08/2024
 * @Time : 10:20 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuResponseDto {
    private Long id;

    private CategoryDto category;

    private ProductDto product;

    private Boolean available;
}
