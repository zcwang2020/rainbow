package com.white.service;


import com.netease.bahamut.purchase.sdk.punchout.request.sp.SpGetRequest;
import com.netease.bahamut.purchase.sdk.punchout.response.sp.SpQuerySupplierResponse;

public interface NetbuyService {


    /**
     * 查询供应商信息
     * @param request
     * @return
     */
    SpQuerySupplierResponse querySupplier(SpGetRequest request);
}
