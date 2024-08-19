/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 12:23 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.service.implement;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.bkk.cafe.dto.EmployeeDto;
import com.bkk.cafe.exception.EntityNotFoundException;
import com.bkk.cafe.model.Employee;
import com.bkk.cafe.repository.EmployeeRepository;
import com.bkk.cafe.service.EmployeeService;
import com.bkk.cafe.util.DtoUtil;
import com.bkk.cafe.util.EntityUtil;

@Service
public class EmployeeServiceImplement implements EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ModelMapper modelMapper;

	private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImplement.class);

	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {
		try {
			Employee employee = DtoUtil.map(employeeDto, Employee.class, modelMapper);
			Employee savedEmployee = EntityUtil.saveEntity(employeeRepository, employee, "Employee");
			return DtoUtil.map(savedEmployee, EmployeeDto.class, modelMapper);
		} catch (DataIntegrityViolationException e) {
			logger.error("Database error while creating employee", e);
			throw new RuntimeException("Database error: " + e.getRootCause().getMessage());
		} catch (Exception e) {
			logger.error("Error creating employee", e);
			throw new RuntimeException("Error creating employee: " + e.getMessage());
		}
	}

	@Override
	public EmployeeDto getEmployeeByStaffId(String staffId) {
		Employee employee = employeeRepository.findEmployeeByStaffId(staffId);
		if(employee == null) {
			throw new EntityNotFoundException("Employee not found with Staff ID: " + staffId);
		} else {
			employee.setId(null);
		}
		return DtoUtil.map(employee, EmployeeDto.class, modelMapper);
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {
		List<Employee> employees = EntityUtil.getAllEntities(employeeRepository);
		return DtoUtil.mapList(employees, EmployeeDto.class, modelMapper);
	}

	@Override
	public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
		Employee existingEmployee = EntityUtil.getEntityById(employeeRepository, id, "Employee");
		modelMapper.map(employeeDto, existingEmployee);
		Employee updatedEmployee = employeeRepository.save(existingEmployee);
		return DtoUtil.map(updatedEmployee, EmployeeDto.class, modelMapper);
	}

	@Override
	public void deleteEmployee(Long id) {
		EntityUtil.deleteEntity(employeeRepository, id, "Employee");
	}
}
