/*
 * @Author : Thant Htoo Aung
 * @Date : 24/08/2024
 * @Time : 11:35 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.service.implement;

import com.bkk.cafe.dto.EmployeeDto;
import com.bkk.cafe.exception.EntityAlreadyExistsException;
import com.bkk.cafe.exception.EntityNotFoundException;
import com.bkk.cafe.model.Employee;
import com.bkk.cafe.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplementTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImplement employeeServiceImplement;

    @SuppressWarnings("resource")
    public EmployeeServiceImplementTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateEmployeeSuccess() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setStaffId("S123");
        employeeDto.setEmail("test@example.com");

        Employee employee = new Employee();
        employee.setEmail("test@example.com");

        when(employeeRepository.existsByEmail(employeeDto.getEmail())).thenReturn(false);
        when(modelMapper.map(employeeDto, Employee.class)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);

        EmployeeDto result = employeeServiceImplement.createEmployee(employeeDto);

        assertNotNull(result);
        assertEquals(employeeDto.getEmail(), result.getEmail());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void testCreateEmployeeAlreadyExists() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmail("test@example.com");

        when(employeeRepository.existsByEmail(employeeDto.getEmail())).thenReturn(true);

        Exception exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            employeeServiceImplement.createEmployee(employeeDto);
        });

        assertEquals("Employee with email 'test@example.com' already exists", exception.getMessage());
    }

    @Test
    public void testGetEmployeeByStaffIdSuccess() {
        Employee employee = new Employee();
        employee.setStaffId("S123");

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setStaffId("S123");

        when(employeeRepository.findEmployeeByStaffId("S123")).thenReturn(Optional.of(employee));
        when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);

        EmployeeDto result = employeeServiceImplement.getEmployeeByStaffId("S123");

        assertNotNull(result);
        assertEquals("S123", result.getStaffId());
    }

    @Test
    public void testGetEmployeeByStaffIdNotFound() {
        when(employeeRepository.findEmployeeByStaffId("S123")).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            employeeServiceImplement.getEmployeeByStaffId("S123");
        });

        assertEquals("Employee not found with Staff ID: S123", exception.getMessage());
    }

    @Test
    public void testGetAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee();
        employee.setStaffId("S123");
        employees.add(employee);

        List<EmployeeDto> employeeDtos = new ArrayList<>();
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setStaffId("S123");
        employeeDtos.add(employeeDto);

        when(employeeRepository.findAll()).thenReturn(employees);
        when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);

        List<EmployeeDto> result = employeeServiceImplement.getAllEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("S123", result.get(0).getStaffId());
    }

    @Test
    public void testUpdateEmployeeSuccess() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setStaffId("S123");

        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);
        existingEmployee.setStaffId("S123");

        when(employeeRepository.findEmployeeByStaffId("S123")).thenReturn(Optional.of(existingEmployee));
        when(modelMapper.map(employeeDto, Employee.class)).thenReturn(existingEmployee);
        when(employeeRepository.save(existingEmployee)).thenReturn(existingEmployee);
        when(modelMapper.map(existingEmployee, EmployeeDto.class)).thenReturn(employeeDto);

        EmployeeDto result = employeeServiceImplement.updateEmployee("S123", employeeDto);

        assertNotNull(result);
        assertEquals("S123", result.getStaffId());
    }


    @Test
    public void testDeleteEmployeeSuccess() {
        Employee employee = new Employee();
        employee.setStaffId("S123");

        when(employeeRepository.findEmployeeByStaffId("S123")).thenReturn(Optional.of(employee));

        employeeServiceImplement.deleteEmployee("S123");

        verify(employeeRepository, times(1)).delete(employee);
    }
}

