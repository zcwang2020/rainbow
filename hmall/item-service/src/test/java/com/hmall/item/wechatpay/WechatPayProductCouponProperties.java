package com.hmall.item.wechatpay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 本地 profile {@code wechatpay-local} 加载，对应 application-wechatpay-local.yaml（gitignore）。
 */
@Data
@ConfigurationProperties(prefix = "wechatpay.product-coupon")
public class WechatPayProductCouponProperties {

    private String apiV3Key;
    private String wechatPayPublicKeyId;
}
