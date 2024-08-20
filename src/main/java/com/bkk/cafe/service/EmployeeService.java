/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 12:20 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.service;

import java.util.List;

import com.bkk.cafe.dto.EmployeeDto;

public interface EmployeeService {
	EmployeeDto createEmployee(EmployeeDto employeeDto);

	EmployeeDto getEmployeeByStaffId(String staffId);

	List<EmployeeDto> getAllEmployees();

	EmployeeDto updateEmployee(String staffId, EmployeeDto employeeDto);

	void deleteEmployee(String staffId);
}
