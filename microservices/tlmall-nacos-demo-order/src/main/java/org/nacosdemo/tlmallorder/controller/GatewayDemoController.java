package org.nacosdemo.tlmallorder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.nacosdemo.tlmallorder.service.OrderService;
import org.nacosdemo.tlmallcommon.BusinessException;
import org.nacosdemo.tlmallcommon.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Slf4j
@Validated
public class GatewayDemoController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper mapper;

    // 批量获取订单
    @RequestMapping("")
    public Result<?> getOrders(
            @RequestParam("userId")
            @NotNull(message = "用户ID不能为空")
            String userId) {
        log.info("根据userId:" + userId + "查询订单信息");
        Result<?> res = null;
        try {
            res = orderService.getOrderByUserId(userId);
        } catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }
        return res;
    }

    // 获取单个订单
    @GetMapping("/{id}")
    public Result<?> getOrderById(
            @PathVariable("id") Integer id) {
        Result<?> res = null;
        try {
            res = orderService.getOrderById(id);
        } catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }
        return res;
    }

    // 返回指定请求头，用于验证Gateway上游AddRequestHeader、自定义过滤器的功能
    // 测试例子：
    // GET http://{gateway_host}:{gateway_port}/orders/request-headers/x-request-color
    @GetMapping("/request-headers/{header_name}")
    public Result getRequestHeader(
            HttpServletRequest request,
            @PathVariable("header_name") @NotNull @NotBlank String headerName) throws Exception {
        String headerValue = request.getHeader(headerName);
        log.info("Gateway请求头{}值为{}", headerName, headerValue);
        return Result.success("返回Gateway请求头", headerName + ": " + headerValue);
    }

    // 返回指定请求参数，用于验证Gateway上游AddRequestParameter的功能
    @GetMapping("/request-params/{param_name}")
    public Result getRequestParam(
            HttpServletRequest request,
            @PathVariable("param_name") @NotNull @NotBlank String paramName) throws Exception {
        String paramValue = request.getParameter(paramName);
        log.info("Gateway请求参数{}值为{}", paramName, paramValue);
        return Result.success("返回Gateway请求参数", paramName + ": " + paramValue);
    }
}

    // validate gateway filter : AddRequestParameter
    /*
    @GetMapping("/request-parameter/color")
    public Result testGateway3(
            @RequestParam("color") String color) throws Exception {
        log.info("gateWay获取请求参数color:" + color);
        return Result.success("返回Gateway添加的请求参数", "color: " + color);
    }
    */


    // validate gateway filter : AddRequestHeader
    /*
    @GetMapping("/request-header/x-request-color")
    public Result getHeaderSamples(
            @RequestHeader("X-Request-color") String color) throws Exception {
        log.info("Gateway获取请求头X-Request-color：" + color);
        return Result.success("返回Gateway添加的Header", "X-Request-color: " + color);
    }
    */
