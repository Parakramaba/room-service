package com.hifigod.roomservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket roomApi() {
        return new Docket(DocumentationType.SWAGGER_2).
                select().
                paths(PathSelectors.ant("/api/**")).
                apis(RequestHandlerSelectors.basePackage("com.hifigod.roomservice")).
                build().
                apiInfo(roomApiInfo());
    }

    private ApiInfo roomApiInfo() {
        return new ApiInfo(
                "HiFiGod - Room Service",
                "API for Room Service",
                "1.0.0",
                "",
                new springfox.documentation.service.Contact("HiFiGod", "", ""),
                "API License",
                "",
                Collections.emptyList()
        );
    }
}
