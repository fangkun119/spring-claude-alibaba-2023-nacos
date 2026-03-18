package org.nacosdemo.tlmallorderconfigdemo.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.nacosdemo.tlmallorderconfigdemo.common.PropertyPlaceholders;
import org.nacosdemo.tlmallorderconfigdemo.common.StaticContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.nacosdemo.tlmallcommon.BusinessException;
import org.nacosdemo.tlmallcommon.Result;
import org.nacosdemo.tlmallorderconfigdemo.dto.OrderDTO;
import org.nacosdemo.tlmallorderconfigdemo.service.OrderService;

@RestController
@RequestMapping("/config-demo")
@Slf4j
@RefreshScope
public class OrderController {
    @Value(PropertyPlaceholders.ORDER_COUNT)
    @Getter
    private int count;

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public Result<?> getOrder(
            @RequestParam("userId") String userId) {
        //模拟异常
        if (("foxxxx").equals(userId)) {
            throw new IllegalArgumentException("非法参数异常");
        }

        // 模拟调用超时
        //        try {
        //            Thread.sleep(6000);
        //        } catch (InterruptedException e) {
        //            throw new RuntimeException(e);
        //        }

        log.info("根据userId:" + userId + "查询订单信息");
        Result<?> res = null;
        try {
            res = orderService.getOrderByUserId(userId);
        } catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }
        return res;
    }

    @PostMapping("/orders")
    public Result<?> addOrder(
            @RequestBody OrderDTO orderDTO) {
        // 远程配置同步实验
        orderDTO.setCount(this.getCount());
        // 对比从Context Environment和从Bean获取的远程配置值
        log.info("count value from context env: {}", StaticContextHolder.getEnvironmentProperty("order.count"));
        log.info("couht value from bean       : {}", this.getCount());
        // 返回结果
        return Result.success(orderDTO);
    }
}
