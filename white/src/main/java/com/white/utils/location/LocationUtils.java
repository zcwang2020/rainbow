package com.white.utils.location;

import cn.hutool.core.lang.Pair;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: tmind
 * @Date: 2024/11/3 12:18
 * @Description:
 */
@Slf4j
public class LocationUtils {

    public static Pair<String, String> getLonAndLat(String address, String key) {
        try {
            // 返回输入地址address的经纬度信息, 格式是 经度,纬度
            String queryUrl = "http://restapi.amap.com/v3/geocode/geo?key=" + key + "&address=" + address;
            // 高德接口返回的是JSON格式的字符串
            String queryResult = getResponse(queryUrl);
            Map<String, String> map = new HashMap<>();
            JSONObject obj = JSONObject.parseObject(queryResult);
            if ("1".equals(obj.get("status").toString()) && Integer.parseInt(obj.get("status").toString()) > 0) {
                List<JSONObject> geocodes = JSONObject.parseArray(obj.get("geocodes").toString(), JSONObject.class);
                String location = geocodes.get(0).get("location").toString();
                System.out.println("经纬度：" + location);
                if (Strings.isNotBlank(location)) {
                    String[] split = location.split(",");
                    return Pair.of(split[0], split[1]);
                }
                return null;
            } else {
                log.error("地址转换经纬度失败，错误码：" + obj.get("infocode"));
            }
        } catch (RuntimeException e) {
            log.info("地址转换经纬度失败", e);
        }
        return null;
    }

    private static String getResponse(String serverUrl) {
        try {
            return HttpUtil.get(serverUrl);
        } catch (Exception e) {
            throw new RuntimeException("请求错误");
        }
    }

    public static Map getLonAndLatBaidu(String address, String key) {
        // 返回输入地址address的经纬度信息, 格式是 经度,纬度
        String queryUrl = "https://api.map.baidu.com/geocoding/v3/?address=" + address + "&output=json" + "&ak=" + key + "&callback=showLocation";
        // 高德接口返回的是JSON格式的字符串
        String queryResult = getResponse(queryUrl);
        System.out.println("queryResult = " + queryResult);
        Map<String, String> map = new HashMap<>();
        JSONObject obj = JSONObject.parseObject(queryResult);

        if (obj.get("status").toString().equals("1")) {
            JSONObject jobJSON = JSONObject.parseObject(obj.get("geocodes").toString().substring(1, obj.get("geocodes").toString().length() - 1));
            String location = jobJSON.get("location").toString();
            System.out.println("经纬度：" + location);
            if (Strings.isNotBlank(location)) {
                String[] lonAndLat = location.split(",");
                map.put("lng", lonAndLat[0]);
                map.put("lat", lonAndLat[1]);
            }
            System.out.println(map);
            return map;
        } else {
            throw new RuntimeException("地址转换经纬度失败，错误码：" + obj.get("infocode"));
        }
    }
}
