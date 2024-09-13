package com.white.meta.request;

import lombok.Data;

@Data
public class AllocateItemData {

    /**
     * 调拨单号
     */
    private int io_id;

    /**
     * 子单号
     */
    private int ioi_id;

    /**
     * 商品编码
     */
    private String sku_id;

    /**
     * 款式编码
     */
    private String i_id;

    /**
     * 数量
     */
    private int qty;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 颜色及规格
     */
    private String properties_value;

    /**
     * 点数
     */
    private int r_qty;

    /**
     * 批次号，需开启相关业务配置
     */
    private String batch_id;

    /**
     * 批次日期，需开启相关业务配置
     */
    private String product_date;

    /**
     * 供应商编号，需开启相关业务配置
     */
    private int supplier_id;

    /**
     * 有效期至，需开启相关业务配置
     */
    private String expiration_date;
}
