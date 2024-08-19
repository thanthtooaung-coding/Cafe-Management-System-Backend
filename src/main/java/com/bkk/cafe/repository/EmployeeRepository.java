/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 12:19 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bkk.cafe.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Employee findEmployeeByStaffId(String staffId);
	Employee findEmployeeByName(String name);
	Employee findEmployeeByPosition(String position);
	Employee findEmployeeByEmail(String email);
	Employee findEmployeeByPhoneNumber(String phoneNumber);
}
