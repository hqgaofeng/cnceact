package com.cnce;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.cnce.*.dao, com.cnce.*.*.dao")
@EnableCaching
@SpringBootApplication
@EnableScheduling
public class ElectricApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElectricApplication.class, args);
    }
}
