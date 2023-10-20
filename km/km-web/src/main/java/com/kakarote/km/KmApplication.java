package com.kakarote.km;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zhangzhiwei
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.kakarote.km.mapper"})
@EnableFeignClients(basePackages = {"com.kakarote"})
public class KmApplication {

    public static void main(String[] args) {
        SpringApplication.run(KmApplication.class, args);
    }
}
