package com.white.meta.request;

import lombok.Data;

import java.util.List;

@Data
public class AllocateResponse extends JstPageData {

    public List<AllocateData> datas;


}
