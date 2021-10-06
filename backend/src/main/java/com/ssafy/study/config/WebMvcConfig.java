package com.ssafy.study.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:8080", "http://localhost:8081", "https://j5a206.p.ssafy.io")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowCredentials(false);
    }


//    @Autowired
//    private JwtInterceptor jwtInterceptor;
//
//    public WebMvcConfig(JwtInterceptor jwtInterceptor) {
//        this.jwtInterceptor = jwtInterceptor;
//    }
//
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry)    {
//        List<String> exclude_list= Arrays.asList("/*","/user/**", "/search/**", "/studio/**", "/fav/**", "/char/**");
//        logger.debug("addInterceptors");
//          registry.addInterceptor(jwtInterceptor)
//                  .excludePathPatterns(exclude_list)
//                  .addPathPatterns("/profile")
//                  .addPathPatterns("/user/signout")
//                  .addPathPatterns("/mypage/**")
//                  .addPathPatterns("/studioedit/**");
//    }
}
