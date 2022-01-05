package com.mszlu.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author by away
 * @date 2022/1/5 11:02
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    /**
     * 跨域配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }
}
