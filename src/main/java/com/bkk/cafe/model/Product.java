/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 11:16 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bkk.cafe.util.ProductStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "product_id", length = 20, nullable = false)
	private String productId;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "price", nullable = false)
	private BigDecimal price;
	
	@Column(name = "quantity", nullable = false)
    private Integer quantity;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private ProductStatus status;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
	
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
