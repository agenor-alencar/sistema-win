package com.win.win_market.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("WIN Market API")
                        .version("1.0.0")
                        .description("API para o sistema WIN Market - Sistema completo de marketplace")
                        .contact(new Contact()
                                .name("Equipe WIN")
                                .email("contato@winmarket.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("${BACKEND_URL}")
                                .description("Servidor de Desenvolvimento"),
                        new Server()
                                .url("${BACKEND_URL_PROD}")
                                .description("Servidor de Produção")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Token JWT para autenticação. Formato: 'Bearer {token}'")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
