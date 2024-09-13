package com.white.meta.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class InventoryRequest extends PageRequest {
    //分仓公司编号，值不传或为0查询所有仓的总库存，传值为指定仓的库存即为库存查询（分仓）界面的数据；编号查询：https://openweb.jushuitan.com/dev-doc?docType=1&docId=3
    public Integer wms_co_id;

    //修改起始时间，和结束时间必须同时存在，时间间隔不能超过七天
    public String modified_begin;

    //修改结束时间，和结束时间必须同时存在，时间间隔不能超过七天
    public String modified_end;

    //商品编码,多个用逗号分隔，与修改时间不能同时为空,最大不超过100个
    public String sku_ids;

    //是否查询库存锁定数
    public Boolean has_lock_qty;

    //商品名称，最多100个，多个商品名称用逗号隔开
    public String names;

    //款式编码
    public String i_ids;

    //时间戳，防漏单，如果用ts查询不需要传时间查询条件
    public Integer ts;

}
