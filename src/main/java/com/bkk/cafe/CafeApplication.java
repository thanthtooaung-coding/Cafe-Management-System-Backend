/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 10:15 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableJpaRepositories
@EnableWebSecurity
public class CafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeApplication.class, args);
	}

}
