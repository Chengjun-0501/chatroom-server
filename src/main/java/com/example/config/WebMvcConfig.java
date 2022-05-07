package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.*;

@Configuration

//跨域实现类，也可以通过CrossOrigin注解实现
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    public CorsInterceptor corsInterceptor;

    @Override
//    跨域实现
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")   //访问IP地址
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")   //请求方法，例如GET
//                .allowedOrigins("*")
                .allowedOriginPatterns("*")   //allowCredentials为true时，AllowedOriginates不能包含特殊值“*”，因为不能在“Access Control Allow Originary”响应头上设置该值，所以用allowedOriginPatterns替代
                .maxAge(3600);
    }

    @Override
//    配置拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
    }
}