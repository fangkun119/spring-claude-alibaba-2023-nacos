package org.nacosdemo.tlmalluseropenfeigndemo.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nacosdemo.tlmalluseropenfeigndemo.controller.data.OrderRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String userId;
    private String commodityCode;
    private Integer count;
    private Integer money;

    public OrderDTO(String userId, String commodityCode) {
        this.userId = userId;
        this.commodityCode = commodityCode;
        this.count = 0;
        this.money = 0;
    }

    public OrderDTO(String userId, OrderRequest orderRequest) {
        this.userId = userId;
        this.commodityCode = orderRequest.getCommodityCode();
        this.count = orderRequest.getCount();
        this.money = orderRequest.getMoney();
    }
}
