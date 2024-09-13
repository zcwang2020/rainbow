package com.white.meta.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PageRequest {

    //第几页，从1开始
    public Integer page_index = 1;

    //默认30，最大不超过100
    public Integer page_size = 100;

    //是否有下一页
    public Boolean has_next;

}
