package com.white.service.impl;

import com.alibaba.fastjson.JSON;
import com.netease.bahamut.dike.sdk.DikeClient;
import com.netease.bahamut.purchase.sdk.punchout.request.sp.SpGetRequest;
import com.netease.bahamut.purchase.sdk.punchout.response.sp.SpQuerySupplierResponse;
import com.white.service.NetbuyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NetbuyServiceImpl implements NetbuyService {


    @Autowired
    private DikeClient dikeClient;

    @Override
    public SpQuerySupplierResponse querySupplier(SpGetRequest request) {
        log.info("[op:querySupplier] request={}", JSON.toJSONString(request));
        return dikeClient.exec(request);
    }
}
