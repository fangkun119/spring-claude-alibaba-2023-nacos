package org.nacosdemo.tlmallorderconfigdemo.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class StaticContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        this.applicationContext = context;
    }

    public static String getEnvironmentProperty(String property) {
        return applicationContext.getEnvironment().getProperty(property);
    }
}