package com.white.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: tmind
 * @Date: 2024/11/5 10:38
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_location")
@Schema(name="Location对象", description="地址坐标信息表")
public class Location {

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    @Excel(name = "订单创建时间")
    private String orderTime;

    private String longitude;

    private String latitude;

    @Schema(description = "创建时间")
    // @TableField("create_time")
    private Long createTime;

    @Schema(description = "更新时间")
    // @TableField("update_time")
    private Long updateTime;

}
