/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 11:01 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.model;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "employee")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "staff_id", length = 20, nullable = false)
	private String staffId;
	
	@Column(name = "name", length = 100, nullable = false)
	private String name;
	
	@Column(name = "position", length = 100, nullable = false)
    private String position;
	
	@Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    
    @Column(name = "hire_date", nullable = false)
    private LocalDateTime hireDate;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@ManyToMany
	@JoinTable(
			name = "employee_role",
			joinColumns = @JoinColumn(name = "employee_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles;

	@PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
