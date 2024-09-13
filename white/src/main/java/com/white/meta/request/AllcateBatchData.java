package com.white.meta.request;

import lombok.Data;

@Data
public class AllcateBatchData {
    /**
     * 批次号
     */
    private String batch_no;

    /**
     * 商品编码
     */
    private String sku_id;

    /**
     * 数量
     */
    private int qty;

    /**
     * 批次日期
     */
    private String product_date;

    /**
     * 供应商编号
     */
    private int supplier_id;

    /**
     * 供应商名称
     */
    private String supplier_name;
}
