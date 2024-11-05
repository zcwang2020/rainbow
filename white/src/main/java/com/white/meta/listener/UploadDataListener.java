package com.white.meta.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import com.white.entity.Location;
import com.white.meta.request.UploadData;
import com.white.service.ILocationService;
import com.white.utils.location.LocationUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: tmind
 * @Date: 2024/11/5 10:24
 * @Description:
 */
@Slf4j
public class UploadDataListener implements ReadListener<Location> {

    @Value("${gaode.key}")
    private String key;

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    /**
     * 缓存的数据
     */
    private List<Location> cachedDataList = new ArrayList<>(BATCH_COUNT);

    private ILocationService locationService;

    public UploadDataListener(ILocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public void onException(Exception e, AnalysisContext analysisContext) throws Exception {

    }

    @Override
    public void invokeHead(Map<Integer, CellData> map, AnalysisContext analysisContext) {

    }

    @Override
    public void invoke(Location data, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = new ArrayList<>(BATCH_COUNT);
        }
    }

    @Override
    public void extra(CellExtra cellExtra, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    @Override
    public boolean hasNext(AnalysisContext analysisContext) {
        return false;
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        for (Location location : cachedDataList) {
            String address = location.getAddress();
            if (StringUtils.isBlank(address)) {
                continue;
            }
            try {
                // 1、根据地址获取经纬度
                String lonAndLat = LocationUtils.getLonAndLat(address, key);
                if (StringUtils.isBlank(lonAndLat)) {
                    location.setLonAndLat(lonAndLat);
                }
            } catch (Exception e) {
                log.error("地址转换经纬度失败, key:{}", key, e);
            }
        }
        locationService.saveBatch(cachedDataList);
        log.info("存储数据库成功！");
    }
}
