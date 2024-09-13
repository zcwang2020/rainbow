package com.white.meta.request;

import lombok.Data;

import java.util.List;

@Data
public class InventoryResponse extends JstPageData {

    public List<InventoryData> inventorys;

    @Data
    public static class InventoryData {

        //商品编码
        public String sku_id;

        //时间戳
        public Long ts;

        //款式编码
        public String i_id;

        //主仓实际库存
        public Integer qty;

        //订单占有数
        public Integer order_lock;

        //仓库待发数
        public Integer pick_lock;

        //虚拟库存
        public Integer virtual_qty;

        //采购在途数
        public Integer purchase_qty;

        //销退仓库存
        public Integer return_qty;

        //进货仓库存
        public Integer in_qty;

        //次品库存
        public Integer defective_qty;

        //修改时间,用此时间作为下一次查询的起始时间
        public String modified;

        //安全库存下限
        public Integer min_qty;

        //安全库存上限
        public Integer max_qty;

        //库存锁定数（是否返回取决于入参时has_lock_qty字段）
        public String lock_qty;

        //商品名称
        public String name;

        //调拨在途数
        public Integer allocate_qty;

    }
}
