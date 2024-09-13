package com.white.meta.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JstPageData {

    private Integer page_index;

    private Boolean has_next;

    private Integer data_count;

    private Integer page_count;

    private Integer page_size;
}
