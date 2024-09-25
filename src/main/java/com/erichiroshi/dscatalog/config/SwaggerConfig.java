package com.erichiroshi.dscatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("API DSCatalog")
						.version("v1.0")
						.contact(new Contact().email("erichiroshi@gmail.com").name("Eric Hiroshi").url("https://github.com/erichiroshi"))
						.description("Gerar token para testes: username: maria@gmail.com password: 123456")
						)
				
				.addSecurityItem(new SecurityRequirement().addList("JWT"))
				.components(new Components()
						.addSecuritySchemes("JWT", new SecurityScheme()
								.type(Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")));
	}
}
