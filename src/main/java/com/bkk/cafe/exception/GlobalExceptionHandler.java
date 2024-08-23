/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 02:10 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.bkk.cafe.util.response.ApiResponse;
import com.bkk.cafe.util.response.ResponseUtil;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
			MethodArgumentNotValidException ex, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed", errors);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> handleDataIntegrityViolationException(
			DataIntegrityViolationException ex) {
		Map<String, String> error = new HashMap<>();
		Throwable rootCause = ex.getRootCause();
		String errorMessage = rootCause != null ? rootCause.getMessage() : "Unknown database error";
		error.put("error", "Database error: " + errorMessage);
		return ResponseUtil.createErrorResponse(HttpStatus.CONFLICT, "Data integrity violation", error);
	}

	@ExceptionHandler(EntityAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<String>> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
		return ResponseUtil.createErrorResponse(HttpStatus.CONFLICT, "Entity already exists", ex.getMessage());
	}

	@ExceptionHandler(UnsupportedOperationException.class)
	public ResponseEntity<ApiResponse<String>> handleUnsupportedOperationException(UnsupportedOperationException ex) {
		return ResponseUtil.createErrorResponse(HttpStatus.NOT_IMPLEMENTED, "Unimplemented method", ex.getMessage());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiResponse<String>> handleEntityNotFoundException(EntityNotFoundException ex) {
		return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Entity not found", ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<String>> handleGlobalExceptions(Exception ex) {
		return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred",
				ex.getMessage());
	}
}
