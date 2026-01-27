package org.nacosdemo.tlmalluseropenfeigndemo.interceptor;

import com.alibaba.nacos.common.utils.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.nacosdemo.tlmalluseropenfeigndemo.common.Constants;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class FeignAuthRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        // 业务逻辑模拟认证逻辑
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (null != attributes) {
            // 解析token
            HttpServletRequest request = attributes.getRequest();
            String authToken = request.getHeader(Constants.AUTHORIZATION);
            if (StringUtils.isEmpty(authToken)) {
                authToken = "";
            }
            // 设置token
            log.info("从Request中解析请求头:{}", authToken);
            template.header(Constants.AUTHORIZATION, authToken);
        }
    }
}

