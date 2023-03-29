package com.cnce.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author chen
 * @date 2017/9/4
 * <p>
 * Email 122741482@qq.com
 * <p>
 * Describe:
 */
/*
当一个类实现了这个接口之后，这个类就可以方便的获得ApplicationContext对象（spring上下文），
Spring发现某个Bean实现了ApplicationContextAware接口，Spring容器会在创建该Bean之后，
自动调用该Bean的setApplicationContext（参数）方法，调用该方法时，会将容器本身ApplicationContext对象作为参数传递给该方法。
 */
@Component
public class ApplicationContextRegister implements ApplicationContextAware {
    //输入日志
    private static Logger logger = LoggerFactory.getLogger(ApplicationContextRegister.class);
    //获取bean
    private static ApplicationContext APPLICATION_CONTEXT;
    /**
     * 设置spring上下文
     * @param applicationContext spring上下文
     * @throws BeansException
     * */
    @Override  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.debug("ApplicationContext registed-->{}", applicationContext);
        APPLICATION_CONTEXT = applicationContext;
    }

    /**
     * 获取容器
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    /**
     * 获取容器对象
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> type) {
        return APPLICATION_CONTEXT.getBean(type);
    }
}