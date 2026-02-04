package org.nacosdemo.tlmallorderconfigdemo.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.nacosdemo.tlmallorderconfigdemo.common.PropertyPlaceholders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RefreshScope // 用于加载配置更新
@Slf4j
// public class OrderConfigScheduler {
public class OrderConfigScheduler implements ApplicationListener<RefreshScopeRefreshedEvent> {
    // 值来自于远程配置order.count
    @Value(PropertyPlaceholders.ORDER_COUNT)
    String count;

    // 触发@RefreshScope执行逻辑会导致@Scheduled定时任务失效
    // 定时任务每隔5s执行一次
    @Scheduled(cron = "*/5 * * * * ?")
    public void execute() {
        log.info("定时任务正常执行：order.count = {}", count);
    }

    @Override
    public void onApplicationEvent(RefreshScopeRefreshedEvent event) {
        // 监听到RefreshScopeRefreshedEvent时，执行一个空的方法，触发Bean重建，恢复失效的定时任务
        log.info("监听到RefreshScopeRefreshedEvent，执行该方法触发Bean重建");
    }
}

