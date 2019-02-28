package com.unidt.helper.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

    /**
     * 对系统后台进行跨域访问进行配置
     */
   // @Configuration
    public class CorsConfig extends WebMvcConfigurerAdapter {
        /*@Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("*")// 1 设置访问源地址
                    .allowCredentials(true)// 2 设置访问源请求头
                    .allowedMethods("GET", "POST", "DELETE", "PUT" ,"OPTIONS") // 3 设置访问源请求方法
                    .maxAge(3600);
        }*/
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig()); // 4 对接口配置跨域设置
//        return new CorsFilter(source);
//    }




}
