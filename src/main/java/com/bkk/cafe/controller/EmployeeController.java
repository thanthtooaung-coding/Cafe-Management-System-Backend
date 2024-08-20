/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 12:42 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bkk.cafe.dto.EmployeeDto;
import com.bkk.cafe.service.EmployeeService;
import com.bkk.cafe.util.response.ApiResponse;
import com.bkk.cafe.util.response.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "CRUD REST APIs for Employee")
@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
	private final EmployeeService employeeService;
	private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@PostMapping
	@Operation(summary = "Create a new employee", description = "Create a new employee. The request body should contain all required fields such as staffId, name, position, email, phoneNumber, and hireDate. The response contains the success message and the status of the created employee.")
	public ResponseEntity<ApiResponse<String>> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
		try {
			logger.info("Creating employee with staffId: {}", employeeDto.getStaffId());
			EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
			logger.info("Employee created with ID: {}", createdEmployee.getId());
			return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Employee created successfully", "created");
		} catch (RuntimeException e) {
			logger.error("Error creating employee", e);
			return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create employee", e.getMessage());
		}
	}

	@GetMapping("/{staffId}")
	@Operation(summary = "Get employee by Staff ID", description = "Retrieve an employee's details by their Staff ID. The response contains the employee's information, including id, staffId, name, position, email, phoneNumber, and hireDate.")
	public ResponseEntity<ApiResponse<EmployeeDto>> getEmployeeById(@PathVariable String staffId) {
		logger.info("Fetching employee with Staff ID: {}", staffId);
		EmployeeDto employee = employeeService.getEmployeeByStaffId(staffId);
		return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Employee fetched successfully", employee);
	}

	@GetMapping
	@Operation(summary = "Get all employees", description = "Retrieve a list of all employees. The response contains a list of employees with their details, including id, staffId, name, position, email, phoneNumber, and hireDate.")
	public ResponseEntity<ApiResponse<List<EmployeeDto>>> getAllEmployees() {
		logger.info("Fetching all employees");
		List<EmployeeDto> employees = employeeService.getAllEmployees();
		if (employees.isEmpty()) {
			return ResponseUtil.createSuccessResponse(HttpStatus.OK, "No employees found", new ArrayList<>());
		}
		return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Employees fetched successfully", employees);
	}

	@PutMapping("/{staffId}")
	@Operation(summary = "Update an existing employee", description = "Update an existing employee. The response is updated Employee object with id, staffId, name, position, email, phoneNumber, hireDate.")
	public ResponseEntity<ApiResponse<String>> updateEmployee(@PathVariable String staffId,
			@Valid @RequestBody EmployeeDto employeeDto) {
		logger.info("Updating employee with Staff ID: {}", staffId);
		employeeService.updateEmployee(staffId, employeeDto);
		return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Employee updated successfully", "updated");
	}

	@DeleteMapping("/{staffId}")
	@Operation(summary = "Delete an employee", description = "Delete an employee by their Staff ID. The response contains a success message indicating that the employee was successfully deleted.")
	public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable String staffId) {
		logger.info("Deleting employee with Staff ID: {}", staffId);
		employeeService.deleteEmployee(staffId);
		return ResponseUtil.createSuccessResponse(HttpStatus.OK, "Employee deleted successfully", "deleted");
	}
}
