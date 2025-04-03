package com.white.controller;

import com.alibaba.fastjson.JSON;
import com.netease.bahamut.purchase.sdk.punchout.request.sp.SpGetRequest;
import com.netease.bahamut.purchase.sdk.punchout.response.sp.SpQuerySupplierResponse;
import com.white.service.NetbuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/netbuy")
public class NetbuyController {

    @Autowired
    private NetbuyService netbuyService;

    @PostMapping("/supplier/query")
    public String querySupplier(@RequestBody SpGetRequest request) {
        SpQuerySupplierResponse spQuerySupplierResponse = netbuyService.querySupplier(request);
        return JSON.toJSONString(spQuerySupplierResponse);
    }
}
