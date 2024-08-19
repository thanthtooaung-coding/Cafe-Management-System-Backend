/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 12:47 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;
}
