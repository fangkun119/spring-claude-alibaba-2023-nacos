package org.nacosdemo.tlmallgateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Component  // 取消注释，可以让这个全局过滤器生效
public class CheckAuthFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(CheckAuthFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取Access Token
        String token = exchange.getRequest().getHeaders().getFirst("token");
        if (null == token) {
            log.info("token is null");
            return getResponseMono(exchange, HttpStatus.UNAUTHORIZED, "Token is missing");
        }
        // 检查Access Token
        log.info("校验token");
        if (!isValid(token)) {
            log.info("token is invalid");
            return getResponseMono(exchange, HttpStatus.FORBIDDEN, "Token is invalid");
        }
        // 调用Filter Chain上面的其它过滤器
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 10;
    }

    boolean isValid(String token) {
        // 模拟Token验证逻辑
        return true;
    }

    Mono<Void> getResponseMono(ServerWebExchange exchange, HttpStatus status, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        response.setStatusCode(status);

        String jsonBody = String.format("{\"code\":%d,\"message\":\"%s\"}", status.value(), message);
        byte[] bytes = jsonBody.getBytes();
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }
}