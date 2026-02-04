package org.nacosdemo.tlmallorderconfigdemo;

import lombok.extern.slf4j.Slf4j;
import org.nacosdemo.tlmallorderconfigdemo.common.StaticContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
@EnableScheduling   // 开启定时任务功能
public class TlmallOrderConfigDemoApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(TlmallOrderConfigDemoApplication.class, args);

        while (true) {
            // 用于配置动态刷新实验，它会打印容器环境中的配置值，迅速观察到Nacos远程配置是否已经同步到环境中
            // printConfigPropertyFromContextEnv("order.count");

            // 每隔10秒执行一次
            sleepSeconds(10);
        }
    }

    private static void printConfigPropertyFromContextEnv(String property) {
        String orderCount = StaticContextHolder.getEnvironmentProperty("order.count");
        log.info("order count from environment: {}", orderCount);
    }

    private static void sleepSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            System.out.println("interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
