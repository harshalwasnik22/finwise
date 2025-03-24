package com.finwise.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI finwiseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FinWise API")
                        .description("REST API for FinWise Financial Management Platform")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("FinWise Team")
                                .email("contact@finwise.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Development Server")
                ));
    }
}