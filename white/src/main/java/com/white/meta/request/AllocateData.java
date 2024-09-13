package com.white.meta.request;

import lombok.Data;

import java.util.List;

@Data
public class AllocateData {

    /**
     * 分仓编号，调拨出为发起方，调拨入为接收方
     */
    private Long wms_co_id;

    /**
     * 公司编号
     */
    private int co_id;

    /**
     * 调拨单号
     */
    private long io_id;

    /**
     * 单据日期
     */
    private String io_date;

    /**
     * 修改时间
     */
    private String modified;

    /**
     * 状态，如 Creating、Confirmed 等
     */
    private String status;

    /**
     * 调拨出仓库名称
     */
    private String warehouse;

    /**
     * 调拨入仓库名称
     */
    private String link_warehouse;

    /**
     * 财务状态，如 Archive、modifing 等
     */
    private String f_status;

    /**
     * 调拨类型，如调拨出
     */
    private String type;

    /**
     * 调拨出仓库编号；主仓=1，销退仓=2，进货仓=3，次品仓=4
     */
    private int wh_id;

    /**
     * 调拨入仓库编号；主仓=1，销退仓=2，进货仓=3，次品仓=4
     */
    private int link_wh_id;

    /**
     * 调拨入分仓编号，调拨出为接收方，调拨入为发起方
     */
    private long link_wms_co_id;

    /**
     * 调拨入关联单号
     */
    private String link_io_id;

    /**
     * 调拨建议号，跨仓调拨入单据才有值
     */
    private String so_id;

    /**
     * 拣货批次号
     */
    private long wave_id;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String creator_name;

    /**
     * 调出虚拟仓编码
     */
    private String lock_wh_id;

    /**
     * 调入虚拟仓编码
     */
    private String lock_link_wh_id;

    /**
     * 外部单号
     */
    private String out_io_id;

    /**
     * 标记，多标签
     */
    private List<String> labels;

    /**
     * 商品集合
     */
    private List<AllocateItemData> items;

    /**
     * 批次集合，获取该节点系统中相关业务项需配置（对应erp基础设置开启生产批次管理 如果是分仓数据 分仓也需要开启）
     */
    private List<AllcateBatchData> batchs;

    private String sns;

    /**
     * 收货人（英文）
     */
    private String receiver_name_en;

    /**
     * 移动电话（英文）
     */
    private String receiver_mobile_en;

    /**
     * 省
     */
    private String receiver_state;

    /**
     * 市
     */
    private String receiver_city;

    /**
     * 区
     */
    private String receiver_district;

    /**
     * 详细地址
     */
    private String receiver_address;

    /**
     * 物流单号
     */
    private String l_id;

    /**
     * 物流公司编码
     */
    private String lc_id;

    /**
     * 物流公司名称
     */
    private String logistics_company;

    /**
     * 单位
     */
    private String unit;
}
