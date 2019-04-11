package com.ssm.base.util;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
//@EnableWebMvc
//@ComponentScan(basePackages = {"com.ssm.*.web"})
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.ssm.*.web"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                //.paths(PathSelectors.regex("^/(ssm|login)/(login|wxlogin|admin)?(/\\w*/?\\w*)*"))
                //.ignoredParameterTypes(ApiIgnore.class);
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SSM API接口管理")
                .description("API 描述")
                .termsOfServiceUrl("http://localhost/swagger-ui.html")//将“url”换成自己的ip:port
                .contact(new Contact("ssm.base", "http://www.zzroamn.com", null))
                .version("v1.0")
                .build();
    }
    
}