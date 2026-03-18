package org.nacosdemo.tlmallgateway.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AddBizDomainGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    private static final Logger log = LoggerFactory.getLogger(AddBizDomainGatewayFilterFactory.class);

    @Override
    public GatewayFilter apply(AbstractNameValueGatewayFilterFactory.NameValueConfig config) {
        return (exchange, chain) -> {
            log.info("调用CheckAuthGatewayFilterFactory:" + config.getName() + ":" + config.getValue());
            // 获取配置的Business Domain
            String bizDomain = config.getValue();
            // 把Business Domain添加到请求头中
            ServerHttpRequest request =  exchange.getRequest();
            if (StringUtils.isNoneBlank(bizDomain)) {
                request = request
                        .mutate()
                        .header("BusinessDomain", bizDomain)
                        .build();
            }
            // 继续执行chain上的其它过滤器
            return chain.filter(exchange.mutate().request(request).build());
        };
    }
}