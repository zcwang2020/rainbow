package com.white.controller;

import com.white.utils.location.LocationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @Author: tmind
 * @Date: 2024/11/3 12:13
 * @Description:
 */
@RestController
@RequestMapping("/location")
public class LocationController {

    @GetMapping("/get")
    public Map<String, String> getLocation(@RequestParam String address, @RequestParam String key) {
        try {
            // 1、根据地址获取经纬度
            Map<String, String> lonAndLat = LocationUtils.getLonAndLat(address, key);
            System.out.println("转换后经纬度为：" + lonAndLat);
            return lonAndLat;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/get/baidu")
    public Map<String, String> getLocationBaidu(@RequestParam String address, @RequestParam String key) {
        try {
            // 1、根据地址获取经纬度
            Map<String, String> lonAndLat = LocationUtils.getLonAndLatBaidu(address, key);
            System.out.println("转换后经纬度为：" + lonAndLat);
            return lonAndLat;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
