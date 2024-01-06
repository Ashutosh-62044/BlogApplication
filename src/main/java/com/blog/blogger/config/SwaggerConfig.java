package com.blog.blogger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    // This class helps us to customize your Swagger Ui....
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.blog.blogger.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getInfo());
    }


    private ApiInfo getInfo(){
        return new ApiInfo(
                "Blogger Application : Backend Implement",
                "This Project is developed by Ashutosh Kumar with the help of pankaj sir",
                "1.0",
                "Terms of service",
                new Contact("Ashutosh kumar", "No website", "ec1994@global.org.in"),
                "License of API",
                "API license URL",
                java.util.Collections.emptyList());
    }

}
