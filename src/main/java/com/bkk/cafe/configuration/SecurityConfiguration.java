/*
 * @Author : Thant Htoo Aung
 * @Date : 11/09/2024
 * @Time : 20:41 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> {
                    authz.requestMatchers("/api/category/**", "/swagger-ui/**", "/v3/api-docs/**", "/login").permitAll();
                    authz.anyRequest().authenticated();
                })
                .oauth2Login(oauth2login -> {
                    oauth2login
                            .successHandler((request, response, authentication) -> response.sendRedirect("http://localhost:5173/#/home"));
                });
        return http.build();
    }
}
