package org.nacosdemo.tlmallgateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

// 用于配置Redis限流，取消@Configuration前面的注释让keyResolver Bean生效
// @Configuration
public class RateLimiterConfig {
    @Bean
    KeyResolver keyResolver() {
        //参数限流
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
    }
}