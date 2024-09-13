package com.white.meta.enums;


import com.white.meta.request.AllocateRequest;
import com.white.meta.request.AllocateResponse;
import com.white.meta.request.InventoryRequest;
import com.white.meta.request.InventoryResponse;

public enum JstInterfaceEnum {
    //https://openweb.jushuitan.com/dev-doc?docType=3&docId=15
    INVENTORY_QUERY("商品库存查询", "/open/inventory/query", InventoryRequest.class, InventoryResponse.class),
    ALLOCATE_QUERY("调拨单查询", "/open/allocate/query", AllocateRequest.class, AllocateResponse.class),
    ;

    private String name;

    private String url;

    private Class req;

    private Class res;

    JstInterfaceEnum(String name, String url, Class req, Class res) {
        this.name = name;
        this.url = url;
        this.req = req;
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
