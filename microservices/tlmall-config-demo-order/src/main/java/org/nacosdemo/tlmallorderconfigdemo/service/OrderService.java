package org.nacosdemo.tlmallorderconfigdemo.service;


import org.springcloudmvp.tlmallcommon.Result;


public interface OrderService {
    Result<?> getOrderByUserId(String userId);
}
