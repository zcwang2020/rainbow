package com.white.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: tmind
 * @Date: 2024/11/5 21:19
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/health")
    public String health(){
        return "health2";
    }
}

