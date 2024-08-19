/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 10:17 AM
 * @Project_Name : cafe
 */
package com.bkk.cafe.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

	@Bean
	public OpenAPI defineOpenApi() {
		Server server = new Server();
		server.setUrl("http://localhost:8080");
		server.setDescription("Development");

		Contact myContact = new Contact();
		myContact.setName("Cafe Management System");
		myContact.setEmail("cafe-contact@gmail.com");

		Info information = new Info().title("Cafe Management System API").version("1.0")
				.description("This API exposes endpoints to manage Cafe Management System.").contact(myContact);
		return new OpenAPI().info(information).servers(List.of(server));
	}
}
