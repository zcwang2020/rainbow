package com.white.meta.request;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.hutool.core.date.DateTime;
import lombok.Data;

/**
 * @Author: tmind
 * @Date: 2024/11/5 10:24
 * @Description:
 */
@Data
public class UploadData {

    @Excel(name = "订单号")
    private String orderId;

    @Excel(name = "收货人省份")
    private String province;

    @Excel(name = "收货人城市")
    private String city;

    @Excel(name = "收货人地区")
    private String district;

    @Excel(name = "详细收货地址/提货地址")
    private String address;

    @Excel(name = "收货人/提货人")
    private String name;

    @Excel(name = "收货人手机号/提货人手机号")
    private String phone;

}
