package com.white.controller;

import com.alibaba.excel.EasyExcel;
import com.white.meta.listener.UploadDataListener;
import com.white.meta.request.UploadData;
import com.white.service.ILocationService;
import com.white.utils.location.LocationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


/**
 * @Author: tmind
 * @Date: 2024/11/3 12:13
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private ILocationService locationService;

    @GetMapping("/get")
    public String getLocation(@RequestParam String address, @RequestParam String key) {
        try {
            // 1、根据地址获取经纬度
            String lonAndLat = LocationUtils.getLonAndLat(address, key);
            System.out.println("转换后经纬度为：" + lonAndLat);
            return lonAndLat;
        } catch (Exception e) {
            log.error("地址转换经纬度失败", e);
            return null;
        }
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

    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), UploadData.class, new UploadDataListener(locationService)).sheet().doRead();
        return "success";
    }
}
