package com.white.meta.request;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author: tmind
 * @Date: 2024/11/5 14:18
 * @Description:
 */
@Data
public class ExportData {

    @Excel(name = "名称", orderNum = "0")
    private String name;

    @Excel(name = "*经度", orderNum = "1")
    private String longitude;

    @Excel(name = "*纬度", orderNum = "2")
    private String latitude;

    @Excel(name = "*地址", orderNum = "3")
    private String address;

    @Excel(name = "颜色", orderNum = "4")
    private String color;

    @Excel(name = "图标(外轮廓)", orderNum = "5")
    private String iconOutline;

    @Excel(name = "图标(填充物)", orderNum = "6")
    private String iconFill;

    @Excel(name = "描述", orderNum = "7")
    private String description;

    @Excel(name = "文件夹", orderNum = "8")
    private String folder;
}
