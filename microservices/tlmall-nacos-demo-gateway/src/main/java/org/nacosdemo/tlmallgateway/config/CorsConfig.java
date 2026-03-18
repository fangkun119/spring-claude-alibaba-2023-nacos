package org.nacosdemo.tlmallgateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 两种方法（使用任意一种即可，不要同时启用)
 * 方法1：通过application.yml配置
 * 方法2：通过下面的@Configuration类来配置
 */
// @Configuration // 取消注释，使此配置类生效
public class CorsConfig {
    @Bean
    public CorsWebFilter corsFilter() {
        // 注意！！
        // 下面代码只是简单演示API如何使用，生产环境上需要更严格的安全限制

        // 创建CORS配置对象
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");   // 允许所有HTTP方法
        config.addAllowedOrigin("*");   // 允许所有来源域（生产环境建议指定具体域名）
        config.addAllowedHeader("*");   // 允许所有请求头
        // 创建基于URL的CORS配置源，用于响应式编程
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);    // 对所有路径（/**）应用上述CORS配置
        // 返回CORS过滤器Bean，Spring会自动将其注册到过滤器链
        return new CorsWebFilter(source);
    }
}