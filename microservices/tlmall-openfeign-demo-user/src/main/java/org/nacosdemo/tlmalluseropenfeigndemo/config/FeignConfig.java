package org.nacosdemo.tlmalluseropenfeigndemo.config;

import feign.Contract;
import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.RequestTemplate;
import feign.codec.Encoder;
import feign.jackson.JacksonEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Type;

/**
 * 配置方式说明：
 * - 方式1：添加 @Configuration 注解，配置全局生效
 * - 方式2：不添加 @Configuration，在 @FeignClient 中通过 configuration 属性指定，局部生效
 *   示例：@FeignClient(name = "tlmall-order", configuration = OrderFeignConfig.class)
 */
// @Configuration // 如果使用方式2，@Configuration就需要注释掉
public class FeignConfig {
    // 日志级别配置
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    // 超时时间配置
    @Bean
    public Request.Options options() {
        return new Request.Options(3000, 5000);
    }

    // @Bean
    // public Contract feignContract() {
    //      return new Contract.Default();
    // }
}