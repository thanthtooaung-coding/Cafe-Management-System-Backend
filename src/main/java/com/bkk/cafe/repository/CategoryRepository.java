/*
 * @Author : Thant Htoo Aung
 * @Date : 21/08/2024
 * @Time : 08:21 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bkk.cafe.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	boolean existsByName(String name);

}
