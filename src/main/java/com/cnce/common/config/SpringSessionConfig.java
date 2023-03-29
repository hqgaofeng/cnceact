package com.cnce.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/*
    通过@ConditionalOnProperty控制配置类是否生效,可以将配置与代码进行分离,实现了更好的控制配置.
    @ConditionalOnProperty实现是通过havingValue与配置文件中的值对比,返回为true则配置类生效,反之失效.
 */
@ConditionalOnProperty(prefix = "bootdo", name = "spring-session-open", havingValue = "true")
public class SpringSessionConfig {

}
