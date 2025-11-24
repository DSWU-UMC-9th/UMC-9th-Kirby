package com.example.umc9th.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swagger() {
        Info info = new Info()
                .title("UMC 9th API")
                .description("UMC API Documentation")
                .version("1.0");

        // JWT 토큰 설정 (지금은 안 씀 — 추후 Login 만들면 사용)
        String securityScheme = "JWT TOKEN";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityScheme);

        Components components = new Components()
                .addSecuritySchemes(securityScheme,
                        new SecurityScheme()
                                .name(securityScheme)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );

        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url("/"))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
