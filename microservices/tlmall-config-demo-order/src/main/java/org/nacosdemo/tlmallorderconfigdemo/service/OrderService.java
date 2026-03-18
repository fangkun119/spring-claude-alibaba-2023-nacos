package org.nacosdemo.tlmallorderconfigdemo.service;


import org.nacosdemo.tlmallcommon.Result;


public interface OrderService {
    Result<?> getOrderByUserId(String userId);
}
