package org.nacosdemo.tlmallorder.controller;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.nacosdemo.tlmallorder.dto.OrderDTO;
import org.nacosdemo.tlmallorder.service.OrderService;
import org.nacosdemo.tlmallcommon.BusinessException;
import org.nacosdemo.tlmallcommon.Result;

@RestController
@RequestMapping("/order")
@Slf4j
@Validated
public class OrderController {
    @Autowired
    private OrderService orderService;

    // 根据用户id查询订单信息
    @RequestMapping("/getOrder")
    public Result<?> getOrder(@RequestParam("userId") @NotNull(message = "用户ID不能为空") String userId) {
        // 模拟异常
        if (("foxxxx").equals(userId)) {
            throw new IllegalArgumentException("非法参数异常");
        }
        // 正常请求
        log.info("根据userId:" + userId + "查询订单信息");
        Result<?> res = null;
        try {
            res = orderService.getOrderByUserId(userId);
        } catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }
        return res;
    }

    @RequestMapping("/getOrderById/{id}")
    public Result<?> getOrderById(@PathVariable("id") Integer id) {
        Result<?> res = null;
        try {
            res = orderService.getOrderById(id);
        } catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }
        return res;
    }


    // 模拟测试openFegin的接口方法规范
    @PostMapping("/post1")
    public Result<?> post1(@RequestBody OrderDTO orderDTO) {
        return Result.success(orderDTO);

    }

    @PostMapping("/post2")
    public Result<?> post2(@RequestBody OrderDTO orderDTO, @RequestParam("token") String token) {
        log.info("token:" + token);
        return Result.success(orderDTO);
    }

    @PostMapping(value = "/post3/{userId}")
    public Result<?> post3(@RequestBody OrderDTO orderDTO, @PathVariable("userId") @NotNull(message = "用户ID不能为空") String userId) {
        return Result.success(orderDTO);
    }
}
