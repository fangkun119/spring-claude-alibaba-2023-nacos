package org.nacosdemo.tlmalluseropenfeigndemo.controller.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String commodityCode = "";
    private Integer count = 0;
    private Integer money = 0;
}

