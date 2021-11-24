package com.agency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.agency.controller")).build().apiInfo(this.getApiInfo())
                .securitySchemes(this.securitySchemes())
                .securityContexts(Arrays.asList(this.securityContext()));

    }

    public SecurityContext securityContext() {
        AuthorizationScope[] scopes = { new AuthorizationScope("read", "for read operation"),
                new AuthorizationScope("write", "for write operation") };
        List<SecurityReference> securityReferences = Arrays.asList(new SecurityReference("basicAuth", scopes),
                new SecurityReference("Key", scopes), new SecurityReference("User Authentification Token", scopes));
        return SecurityContext.builder().securityReferences(securityReferences)
                .forPaths(PathSelectors.any())
                .build();
    }

    public List<SecurityScheme> securitySchemes() {
        // SecurityScheme basicAuth = new BasicAuth("basicAuth");
        SecurityScheme userAuthToken = new ApiKey("User Authentification Token", "Authorization", "header");
        //  SecurityScheme keyAuth = new ApiKey("Key", "Key", "header");
        return Arrays.asList(
                // keyAuth,
                userAuthToken
                //, basicAuth
        );
    }



    private ApiInfo getApiInfo() {
        Contact contact = new Contact("AGENCY", "http://www.google.com", "FFFFFFF@gmail.com");
        return new ApiInfoBuilder()
                .title("REST API FOR AGENCY.")
                .description("This page show all ressources that you could handle to communicate with database via REST API.")
                .version("1.0")
                .license(" v.1.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact)
                .build();
    }



}