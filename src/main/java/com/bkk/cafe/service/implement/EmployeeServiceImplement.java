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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bkk.cafe.dto.EmployeeDto;
import com.bkk.cafe.exception.EntityAlreadyExistsException;
import com.bkk.cafe.exception.EntityNotFoundException;
import com.bkk.cafe.model.Employee;
import com.bkk.cafe.repository.EmployeeRepository;
import com.bkk.cafe.service.EmployeeService;
import com.bkk.cafe.util.DtoUtil;
import com.bkk.cafe.util.EntityUtil;
import com.bkk.cafe.util.response.PaginationResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImplement implements EmployeeService {
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImplement.class);

	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {
		try {
			logger.debug("Attempting to create a new employee with Staff ID: {}", employeeDto.getStaffId());

			logger.debug("Checking if an employee with email '{}' already exists", employeeDto.getEmail());
			if (employeeRepository.existsByEmail(employeeDto.getEmail())) {
				String errorMessage = "Employee with email '" + employeeDto.getEmail() + "' already exists";
				logger.error(errorMessage);
				throw new EntityAlreadyExistsException(errorMessage);
			}
			Employee employee = DtoUtil.map(employeeDto, Employee.class, modelMapper);

			logger.debug("Saving new employee with Staff ID: {} to the database", employeeDto.getStaffId());
			Employee savedEmployee = EntityUtil.saveEntity(employeeRepository, employee, "Employee");
			logger.debug("Employee with Staff ID: {} successfully created with ID: {}", savedEmployee.getStaffId(), savedEmployee.getId());

			EmployeeDto createdEmployeeDto = DtoUtil.map(savedEmployee, EmployeeDto.class, modelMapper);
			createdEmployeeDto.setId(null);
			return createdEmployeeDto;
		} catch (EntityAlreadyExistsException e) {
			logger.warn("Failed to create employee due to existing email: {}", employeeDto.getEmail());
			throw e;
		} catch (Exception e) {
			logger.error("Unexpected error occurred while creating employee with Staff ID: {}", employeeDto.getStaffId(), e);
			throw new RuntimeException("Unexpected error creating employee: " + e.getMessage());
		}
	}

	private Employee findEmployeeByStaffId(String staffId) {
		logger.debug("Attempting to retrieve employee with Staff ID: {}", staffId);
		return employeeRepository.findEmployeeByStaffId(staffId).orElseThrow(() -> {
			logger.error("Employee not found with Staff ID: {}", staffId);
			return new EntityNotFoundException("Employee not found with Staff ID: " + staffId);
		});
	}

	@Override
	public EmployeeDto getEmployeeByStaffId(String staffId) {
		Employee employee = findEmployeeByStaffId(staffId);
		employee.setId(null);
		return DtoUtil.map(employee, EmployeeDto.class, modelMapper);
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {
		logger.debug("Attempting to retrieve all employees from the database");
		List<Employee> employees = EntityUtil.getAllEntities(employeeRepository);
		logger.debug("Successfully retrieved {} employees from the database", employees.size());
		List<EmployeeDto> employeeDtos = DtoUtil.mapList(employees, EmployeeDto.class, modelMapper);
		employeeDtos.forEach(dto -> dto.setId(null));
		return employeeDtos;
	}

	@Override
	public EmployeeDto updateEmployee(String staffId, EmployeeDto employeeDto) {
		logger.debug("Attempting to update employee with Staff ID: {}", staffId);
		Employee existingEmployee = findEmployeeByStaffId(staffId);
		Long id = existingEmployee.getId();
		modelMapper.map(employeeDto, existingEmployee);
		existingEmployee.setId(id);
		Employee updatedEmployee = employeeRepository.save(existingEmployee);
		logger.debug("Employee with Staff ID: {} updated in the database", staffId);
		updatedEmployee.setId(null);
		return DtoUtil.map(updatedEmployee, EmployeeDto.class, modelMapper);
	}

	@Override
	public void deleteEmployee(String staffId) {
		logger.debug("Attempting to delete employee with Staff ID: {}", staffId);
		Employee employee = findEmployeeByStaffId(staffId);
		employeeRepository.delete(employee);
		logger.debug("Employee with Staff ID: {} deleted from the database", staffId);
	}

	@Override
	public PaginationResponse<EmployeeDto> getEmployeesWithPagination(int page, int size, String search) {
	    logger.debug("Attempting to retrieve employees with pagination - Page: {}, Size: {}, Search: {}", page, size, search);

	    Pageable pageable = PageRequest.of(page, size);
	    Page<Employee> employeePage;

	    if (search == null || search.trim().isEmpty()) {
	        employeePage = employeeRepository.findAll(pageable);
	    } else {
	        employeePage = employeeRepository.searchEmployees(search, pageable);
	    }

	    logger.debug("Retrieved {} employees for Page: {}, Size: {}", employeePage.getNumberOfElements(), page, size);
	    logger.debug("Pagination metadata - Total Pages: {}, Total Elements: {}", employeePage.getTotalPages(), employeePage.getTotalElements());

	    List<EmployeeDto> employeeDtos = employeePage.map(employee -> {
	        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
	        employeeDto.setId(null);
	        return employeeDto;
	    }).getContent();

	    logger.debug("Mapped {} Employee entities to EmployeeDto objects", employeeDtos.size());

	    PaginationResponse<EmployeeDto> response = PaginationResponse.<EmployeeDto>builder()
    	    .content(employeeDtos)
    	    .pageNumber(employeePage.getNumber())
    	    .pageSize(employeePage.getSize())
    	    .totalElements(employeePage.getTotalElements())
    	    .totalPages(employeePage.getTotalPages())
    	    .build();

	    logger.debug("Successfully constructed PaginationResponse with Page Number: {}, Page Size: {}", response.getPageNumber(), response.getPageSize());

	    return response;
	}

}
