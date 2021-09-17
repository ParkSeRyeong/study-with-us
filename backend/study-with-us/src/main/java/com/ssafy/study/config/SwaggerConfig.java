package com.ssafy.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * API 문서 관련 swagger2 설정 정의.
 */

/* 스웨거 링크 : http://localhost:8080/swagger-ui.html */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

    private String version = "V1";
    private String title = "STUDY-WITH-US API " + version;
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    /**
     * api 구조를 만들어줄 범위를 지정한다
     * */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
                .apiInfo(apiInfo())     // 이 apiInfo는 바로 아래 select()보다 무조건 위에 있어야하는 것 같다!
                .select()
                .apis(RequestHandlerSelectors.any())        // 현재 RequestMapping으로 할당된 모든 URL 리스트를 추출
          //      .paths(PathSelectors.ant("/**"))    // 그 중 /api/** 인 url들만 필터링
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.study.api.controller"))
                .build()
//                .securityContexts(newArrayList(securityContext()))
//               .securitySchemes(newArrayList(apiKey()))
                ;
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().
                title("Study With Us Swagger")
                .version("V1")
                .description("AI 기술을 활용한 딴짓 방지 스터디 관리 플랫폼")
                .build();

    }

//    private ApiKey apiKey() {
//        return new ApiKey(SECURITY_SCHEMA_NAME, "Authorization", "header");
//    }
//
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    public static final String SECURITY_SCHEMA_NAME = "JWT";
    public static final String AUTHORIZATION_SCOPE_GLOBAL = "global";
    public static final String AUTHORIZATION_SCOPE_GLOBAL_DESC = "accessEverything";

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(AUTHORIZATION_SCOPE_GLOBAL, AUTHORIZATION_SCOPE_GLOBAL_DESC);
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(new SecurityReference(SECURITY_SCHEMA_NAME, authorizationScopes));
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
//                .supportedSubmitMethods(newArrayList("get").toArray(new String[0])) // try it 기능 활성화 범위
//                .operationsSorter(METHOD)
                .build();
    }
}