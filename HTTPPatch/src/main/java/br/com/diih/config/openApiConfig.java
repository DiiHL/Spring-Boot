package br.com.diih.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class openApiConfig {

    @Bean(name = "OpenApiConfigBean")
    OpenAPI openApiConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("REST API's RESTfull From 0 with Java, Spring Boot, Kubernetes and Docker")
                        .version("v1")
                        .description("REST API's RESTfull From 0 with Java, Spring Boot, Kubernetes and Docker V1")
                        .license(new License()
                                .name("Apache 2.0")));
    }
}
