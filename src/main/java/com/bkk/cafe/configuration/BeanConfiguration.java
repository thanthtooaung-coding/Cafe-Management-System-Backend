/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 10:19 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
