package com.white.meta.request;

import com.google.gson.JsonObject;
import com.white.meta.enums.JstCode;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JstResponse<T> {

    private int code;

    private String msg;

    private JsonObject data;

    public boolean isSuccess(){
        return this.code == JstCode.SUCCESS.getCode();
    }


}
