package com.white.controller;

import cn.afterturn.easypoi.csv.CsvImportUtil;
import cn.afterturn.easypoi.csv.entity.CsvImportParams;
import cn.hutool.core.lang.Pair;
import com.alibaba.fastjson.JSON;
import com.white.entity.Location;
import com.white.meta.request.ExportData;
import com.white.meta.request.UploadData;
import com.white.service.ExcelService;
import com.white.service.ILocationService;
import com.white.utils.location.LocationUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    @Value("${gaode.key}")
    private String key;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private ILocationService locationService;

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    @GetMapping("/get")
    public String getLocation(@RequestParam String address, @RequestParam String key) {
        try {
            // 1、根据地址获取经纬度
            Pair<String, String> lonAndLat = LocationUtils.getLonAndLat(address, key);
            System.out.println("转换后经纬度为：" + lonAndLat);
            return JSON.toJSONString(lonAndLat);
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

    @PostMapping("/get/location")
    @ResponseBody
    public void upload(@RequestPart("file") MultipartFile file, HttpServletResponse response) {
        log.info("上传文件：{}", file.getOriginalFilename());
        if (file.isEmpty()) {
            return;
        }
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        List<UploadData> uploadDataList;
        if ("xlsx".equalsIgnoreCase(fileExtension) || "xls".equalsIgnoreCase(fileExtension)) {
            uploadDataList = handleExcelFile(file);
        } else if ("csv".equalsIgnoreCase(fileExtension)) {
            uploadDataList = handleCsvFile(file);
        } else {
            throw new RuntimeException("不支持的文件类型");
        }
        ArrayList<Location> locationList = new ArrayList<>(BATCH_COUNT);
        List<ExportData> exportDataList = new ArrayList<>(uploadDataList.size());
        if (CollectionUtils.isNotEmpty(uploadDataList)) {
            for (UploadData uploadData : uploadDataList) {
                Location location = new Location();
                BeanUtils.copyProperties(uploadData, location);
                location.setCreateTime(System.currentTimeMillis());
                location.setUpdateTime(System.currentTimeMillis());
                String address = uploadData.getAddress();
                if (StringUtils.isNotBlank(address)) {
                    Pair<String, String> lonAndLat = LocationUtils.getLonAndLat(address, key);
                    if (null != lonAndLat) {
                        location.setLongitude(lonAndLat.getKey());
                        location.setLatitude(lonAndLat.getValue());
                    }
                }
                locationList.add(location);
                ExportData exportData = new ExportData();
                exportData.setName(uploadData.getAddress());
                exportData.setLongitude(location.getLongitude());
                exportData.setLatitude(location.getLatitude());
                exportData.setAddress(uploadData.getProvince() + uploadData.getCity() + uploadData.getDistrict() + address);
                exportData.setDescription(uploadData.getName() + " " + uploadData.getPhone() + "订单号" + uploadData.getOrderId());
                exportDataList.add(exportData);
                // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
                if (locationList.size() >= BATCH_COUNT) {
                    locationService.saveBatch(locationList);
                    // 存储完成清理 list
                    locationList = new ArrayList<>(BATCH_COUNT);
                }
            }
            if (CollectionUtils.isNotEmpty(locationList)) {
                locationService.saveBatch(locationList);
            }
        }
        excelService.exportExcel(exportDataList, response);
    }

    private List<UploadData> handleCsvFile(MultipartFile file) {
        try {
            CsvImportParams params = new CsvImportParams();
            params.setHeadRows(1);
            return CsvImportUtil.importCsv(file.getInputStream(), UploadData.class, params);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<UploadData> handleExcelFile(MultipartFile file) {
        return excelService.importExcel(file);
    }

    private String getFileExtension(String filename) {
        int lastIndexOfDot = filename.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return "";
        }
        return filename.substring(lastIndexOfDot + 1).toLowerCase();
    }
}
