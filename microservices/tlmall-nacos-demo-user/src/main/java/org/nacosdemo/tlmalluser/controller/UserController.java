package org.nacosdemo.tlmalluser.controller;

import lombok.extern.slf4j.Slf4j;
import org.nacosdemo.tlmalluser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.nacosdemo.tlmallcommon.Result;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getOrder")
    public Result<?> getOrderByUserId(@RequestParam("userId") String userId) {
        log.info("根据userId:"+userId+"查询订单信息");
        return userService.getOrderByUserId(userId);
    }
}