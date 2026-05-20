package com.hmall.item.wechatpay;

import lombok.Data;

/**
 * 对齐严选 {@code WechatPayMerchantConfig}（Apollo merchant.config.list 单条）。
 */
@Data
public class WechatPayMerchantConfig {

    private String merchantCode;
    private String brandId;
    private String appid;
    private String apiV3Key;
    private String certificateSerialNo;
    private String wechatPayPublicKeyId;
}
