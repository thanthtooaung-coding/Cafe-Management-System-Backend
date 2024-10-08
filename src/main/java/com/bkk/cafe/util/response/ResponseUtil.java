/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 12:45 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.util.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> createSuccessResponse(
            HttpStatus httpStatus, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(httpStatus, message, data);
        return ResponseEntity.status(httpStatus).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> createErrorResponse(
            HttpStatus httpStatus, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(httpStatus, message, data);
        return ResponseEntity.status(httpStatus).body(response);
    }

}
