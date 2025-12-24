package org.nacosdemo.tlmalluser.listener;

import lombok.extern.slf4j.Slf4j;
import org.springcloudmvp.tlmallcommon.Result;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.retry.support.RetryTemplate;


@Component
@Slf4j
public class StartupDataLoader implements CommandLineRunner {
    private final RestTemplate restTemplate;

    public StartupDataLoader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 模拟预加载数据，同步执行远程调用
    @Override
    public void run(String... args) {
        log.info("应用启动完成，开始初始化远程数据...");

        try {
            // 1. 设置超时
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(8000);
            factory.setReadTimeout(8000);
            restTemplate.setRequestFactory(factory);

            // 2. 执行远程调用（带重试）
            Result<?> result = RetryTemplate.builder()
                    .maxAttempts(5)
                    .fixedBackoff(2000)
                    .retryOn(Exception.class)
                    .build()
                    .execute(context -> {
                        log.info("尝试第{}次加载数据", context.getRetryCount() + 1);
                        String url = "http://tlmall-order/order/getOrder?userId=fox";
                        return restTemplate.getForObject(url, Result.class);
                    });
            log.info("启动数据加载成功: {}", result);
        } catch (Exception e) {
            log.error("启动数据加载失败", e);
            throw e;
        }

        log.info("远程数据初始化完成");
    }
}