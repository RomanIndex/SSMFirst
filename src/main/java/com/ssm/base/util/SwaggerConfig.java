package com.ssm.base.util;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo())
        		//.pathMapping("/admin")
        		.select()
        		//.paths(PathSelectors.ant("/match/*"))
        		.paths(PathSelectors.regex("^/(match|login)/(login|wxlogin|register|wxregister|member|team|hospital|verify|media|exam|common|check)?(/\\w*/?\\w*)*"))
        		.build();
        		//.ignoredParameterTypes(ApiIgnore.class);

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "SSM API接口管理", // title 标题
                "SSM API接口文档", // description 描述 标题下
                "1.0.0", // version
                "http://www.winwayworld.com", // termsOfServiceUrl
                "Winway", // contactName
                "Apache 2.0", // licence
                "http://www.apache.org/licenses/LICENSE-2.0.html" // licenceUrl
        );

    }
    
}