package com.white.meta.request;

import com.alibaba.fastjson.JSON;
import com.white.meta.constant.JstConstant;
import com.white.meta.utils.JstSignUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class JstRequest<T> {

    private String app_key = "e980f39a33304514bf2035bebb693f93";

    private String access_token = "2e948dd543e44b2eb327e05883194433";

    private String timestamp;

//    private String grant_type;

    private String version = "2";

    private String charset = StringUtils.lowerCase(StandardCharsets.UTF_8.name());

    private String sign;

    private T biz;

    public String getTimestamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    public String getSign() {
        Map<String, String> map = new HashMap<>();
        map.put("app_key", this.app_key);
        map.put("access_token", this.access_token);
        map.put("timestamp", this.getTimestamp());
        map.put("version", this.version);
        map.put("charset", this.charset);
        map.put("sign", this.sign);
        map.put("biz", JSON.toJSONString(this.biz));
        return JstSignUtil.getSign(JstConstant.appSecret, map);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("app_key", this.app_key);
        map.put("access_token", this.access_token);
        map.put("timestamp", this.getTimestamp());
        map.put("version", this.version);
        map.put("charset", this.charset);
        map.put("sign", this.getSign());
        map.put("biz", JSON.toJSONString(this.biz));
        return map;
    }
}
