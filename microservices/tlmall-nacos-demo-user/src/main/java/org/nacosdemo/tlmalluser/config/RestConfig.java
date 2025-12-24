package org.nacosdemo.tlmalluser.config;


import org.nacosdemo.tlmalluser.config.loadbalancer.RandomLoadBalancerConfig;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
@LoadBalancerClients(defaultConfiguration = RandomLoadBalancerConfig.class)
public class RestConfig {

    /**
     * 用于分析 @LoadBalanced 底层实现
     *
     * RestTemplate默认装配方法
     *   在Spring Boot启动阶段
     *   - 只是一个默认的RestTemplate
     *   - 并没有Spring Boot LoadBalancer注入
     *   - 因此无法调用下游
     *   要Spring Boot启动完成之后，才可以正常使用
     */
      @Bean
      @LoadBalanced
      public RestTemplate restTemplate() {
        return new RestTemplate();
      }

     /**
     * RestTemplate装配方法改进
     *   - 在装配阶段主动注入Spring Bool LoadBalancer拦截器
     *   - 可以在启动阶段调用下游，获取初始化所需的数据
     */
//    @Bean
//    public RestTemplate restTemplate(LoadBalancerInterceptor loadBalancerInterceptor) {
//        RestTemplate restTemplate = new RestTemplate();
//        // 注入loadBalancerInterceptor拦截器（具有负载均衡的能力）
//        restTemplate.setInterceptors(Arrays.asList(loadBalancerInterceptor));
//        return restTemplate;
//    }
}
