package com.ck.labmanagesystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("高校实验室管理系统API")
                        .version("1.0")
                        .description("基于Spring Boot + MyBatis-Plus的实验室管理系统"));
    }
}
