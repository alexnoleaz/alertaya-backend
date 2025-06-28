package com.alertaya.backend.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class ApplicationOpenApiConfig {
	private static final String SECURITY_SCHEME_NAME = "bearerAuth";

	@Bean
	OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("AlertaYa API")
						.version("v1")
						.description("AlertaYa API for the AlertaYa application, built with Spring Boot.")
						.contact(new Contact()
								.name("Alexander Nole")
								.url("https://www.linkedin.com/in/alexnoleaz")
								.email("")))
				.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
				.components(new Components()
						.addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
								.name("Authorization")
								.description(
										"JWT Authorization header using the Bearer scheme. Example: \"Authorization: Bearer {token}\"")
								.in(SecurityScheme.In.HEADER)
								.type(SecurityScheme.Type.APIKEY)
								.scheme("bearer")));
	}
}
