package com.hmall.address;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.hmall.address.mapper")
@SpringBootApplication
public class AddressApplication {
    public static void main(String[] args) {
        SpringApplication.run(AddressApplication.class, args);
    }
}