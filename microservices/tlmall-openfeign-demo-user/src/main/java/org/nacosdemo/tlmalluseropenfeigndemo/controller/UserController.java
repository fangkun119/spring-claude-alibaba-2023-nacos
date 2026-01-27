package org.nacosdemo.tlmalluseropenfeigndemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.nacosdemo.tlmalluseropenfeigndemo.controller.data.OrderRequest;
import org.nacosdemo.tlmalluseropenfeigndemo.feign.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springcloudmvp.tlmallcommon.Result;
import org.nacosdemo.tlmalluseropenfeigndemo.feign.OrderFeignService;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private OrderFeignService orderService;

    @GetMapping(value = "/{userId}/orders")
    public Result<?> getUserOrders(
            @PathVariable("userId") String userId) {
        log.info("根据userId:" + userId + "查询订单信息");

        // 使用openFeign调用订单服务
        Result result = orderService.getOrder(userId);
        return result;
    }

    @PostMapping(value = "/{userId}/orders")
    public Result<?> addUserOrder(
            @PathVariable("userId") String userId,
            @RequestBody OrderRequest orderRequest) {
        OrderDTO orderDTO = new OrderDTO(userId, orderRequest);

        //测试 application/json
        //Result result = orderService.post1(orderDTO);
        //Result result = orderService.post2(orderDTO,"xxxxxxxxxxxxx");
        Result result = orderService.post3(orderDTO,userId);

        //返回结果
        return result;
    }
}