/*
 * @Author : Thant Htoo Aung
 * @Date : 21/08/2024
 * @Time : 08:02 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String redirectToSwagger() {
		return "redirect:/swagger-ui.html";
	}
}
