package com.hmall.cart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


// 方式1：扫描指定包
@EnableFeignClients(basePackages = {"com.hmall.api.client"})
// 方式2：扫描指定类型
// @EnableFeignClients(basePackageClasses = {com.hmall.api.client.ItemClient.class})
// 方式3：扫描指定包
// @ComponentScan("com.hmall.api")
@MapperScan("com.hmall.cart.mapper")
@SpringBootApplication
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }
}


