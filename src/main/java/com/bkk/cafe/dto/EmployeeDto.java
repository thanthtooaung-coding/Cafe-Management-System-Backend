/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 12:10 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {

	private Long id;

	@NotBlank(message = "Staff ID is required")
	@Size(max = 20, message = "Staff ID must be at most 20 characters long")
	private String staffId;

	@NotBlank(message = "Name is required")
	@Size(max = 100, message = "Name must be at most 100 characters long")
	private String name;

	@NotBlank(message = "Position is required")
	@Size(max = 100, message = "Position must be at most 100 characters long")
	private String position;

	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	@Size(max = 255, message = "Email must be at most 255 characters long")
	private String email;

	@NotBlank(message = "Phone Number is required")
	@Size(max = 20, message = "Phone number must be at most 20 characters long")
	private String phoneNumber;

	@NotNull(message = "Hire date is required")
	private LocalDateTime hireDate;
}
