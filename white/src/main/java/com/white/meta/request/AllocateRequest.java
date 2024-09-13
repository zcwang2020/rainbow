package com.white.meta.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class AllocateRequest implements Serializable {
    private static final long serialVersionUID = -2892593600844758129L;

    /**
     * 修改起始时间，和结束时间必须同时存在，时间间隔不能超过七天，与线上单号不能同时为空
     */
    private String modified_begin;

    /**
     * 修改结束时间，和起始时间必须同时存在，时间间隔不能超过七天，与线上单号不能同时为空
     */
    private String modified_end;

    /**
     * 指定线上订单号，和时间段不能同时为空
     */
    private List<String> so_ids;

    /**
     * 第几页，从第一页开始，默认1
     */
    private int page_index = 1;

    /**
     * 每页多少条，默认30，最大50
     */
    private int page_size = 50;

    /**
     * 调拨单号
     */
    private List<String> io_ids;

    /**
     * 调拨类型（调拨出，调拨入）
     */
    private String type;

    /**
     * 0:修改时间，modified。 2:出入库时间 io_date，未传入时默认为0
     */
    private int date_type;


}
