/*
 * @Author : Thant Htoo Aung
 * @Date : 24/08/2024
 * @Time : 11:06 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Employee> employees = new HashSet<>();
}