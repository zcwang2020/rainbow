package com.white.meta.request;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author: tmind
 * @Date: 2024/11/5 10:24
 * @Description:
 */
@Data
public class UploadData {

    @Excel(name = "省份")
    private String province;

    @Excel(name = "城市")
    private String city;

    @Excel(name = "区县")
    private String district;

    @Excel(name = "详细地址")
    private String address;
}
