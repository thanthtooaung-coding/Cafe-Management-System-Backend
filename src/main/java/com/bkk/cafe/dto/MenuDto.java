/*
 * @Author : Thant Htoo Aung
 * @Date : 24/08/2024
 * @Time : 08:27 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDto {
    private Long id;
    @NotNull(message = "Category ID is required")
    @Min(value = 1, message = "Category ID must be greater than 0")
    private Long categoryId;

    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotNull(message = "Availability status is required")
    private Boolean available;
}
