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
import org.springframework.stereotype.Service;

import com.bkk.cafe.dto.EmployeeDto;
import com.bkk.cafe.exception.EntityAlreadyExistsException;
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
			if (employeeRepository.existsByEmail(employeeDto.getEmail())) {
				String errorMessage = "Employee with email '" + employeeDto.getEmail() + "' already exists";
				logger.error(errorMessage);
				throw new EntityAlreadyExistsException(errorMessage);
			}
			Employee employee = DtoUtil.map(employeeDto, Employee.class, modelMapper);
			Employee savedEmployee = EntityUtil.saveEntity(employeeRepository, employee, "Employee");
			return DtoUtil.map(savedEmployee, EmployeeDto.class, modelMapper);
		} catch (EntityAlreadyExistsException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Unexpected error creating employee", e);
			throw new RuntimeException("Unexpected error creating employee: " + e.getMessage());
		}
	}

	@Override
	public EmployeeDto getEmployeeByStaffId(String staffId) {
		Employee employee = employeeRepository.findEmployeeByStaffId(staffId)
				.orElseThrow(() -> new EntityNotFoundException("Employee not found with Staff ID: " + staffId));
		employee.setId(null);
		return DtoUtil.map(employee, EmployeeDto.class, modelMapper);
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {
		List<Employee> employees = EntityUtil.getAllEntities(employeeRepository);
		return DtoUtil.mapList(employees, EmployeeDto.class, modelMapper);
	}

	@Override
	public EmployeeDto updateEmployee(String staffId, EmployeeDto employeeDto) {
		Employee existingEmployee = employeeRepository.findEmployeeByStaffId(staffId)
				.orElseThrow(() -> new EntityNotFoundException("Employee not found with Staff ID: " + staffId));
		Long id = existingEmployee.getId();
		modelMapper.map(employeeDto, existingEmployee);
		existingEmployee.setId(id);
		Employee updatedEmployee = employeeRepository.save(existingEmployee);
		updatedEmployee.setId(null);
		return DtoUtil.map(updatedEmployee, EmployeeDto.class, modelMapper);
	}

	@Override
	public void deleteEmployee(String staffId) {
		Employee employee = employeeRepository.findEmployeeByStaffId(staffId)
				.orElseThrow(() -> new EntityNotFoundException("Employee not found with Staff ID: " + staffId));

		employeeRepository.delete(employee);
	}
}
