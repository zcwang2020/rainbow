package com.hmall.item.wechatpay;

import com.alibaba.fastjson.JSON;
import okhttp3.Headers;
import java.security.PublicKey;
import java.util.List;

/**
 * 对齐严选 {@code ProductCouponNotifyAppService#parseNotificationOrThrow}，
 * 供 rainbow/item-service 本地单测，不依赖严选工程启动。
 */
public class ProductCouponNotifyParseSupport {

    private static final String HEADER_WECHATPAY_SERIAL = "Wechatpay-Serial";

    private final List<WechatPayMerchantConfig> merchantList;
    private final String defaultMerchantCode;

    public ProductCouponNotifyParseSupport(List<WechatPayMerchantConfig> merchantList, String defaultMerchantCode) {
        this.merchantList = merchantList;
        this.defaultMerchantCode = defaultMerchantCode;
    }

    /** MCH001 + 线上 Apollo 常用配置，便于单测直接 new。 */
    public static ProductCouponNotifyParseSupport forMch001(String apiV3Key, String wechatPayPublicKeyId) {
        WechatPayMerchantConfig config = new WechatPayMerchantConfig();
        config.setMerchantCode("MCH001");
        config.setApiV3Key(apiV3Key);
        config.setWechatPayPublicKeyId(wechatPayPublicKeyId);
        return new ProductCouponNotifyParseSupport(java.util.Collections.singletonList(config), "MCH001");
    }

    /**
     * 与严选线上一致：resolveMerchantConfig → 加载公钥 → 验签 → Gson → 解密。
     */
    public WXPayBrandUtility.Notification parseNotificationOrThrow(Headers headers, String body) {
        String wechatpaySerial = headers.get(HEADER_WECHATPAY_SERIAL);
        WechatPayMerchantConfig config = resolveMerchantConfig(wechatpaySerial);
        System.out.println("[ProductCouponNotify] step=parse_merchant_config, wechatpaySerial="
                + wechatpaySerial + ", config=" + JSON.toJSONString(config));
        if (config == null || isBlank(config.getApiV3Key()) || isBlank(config.getWechatPayPublicKeyId())) {
            System.out.println("[ProductCouponNotify] step=parse_fail, reason=merchant_config_invalid");
            throw new NotifyParseException("merchant config missing apiV3/publicKeyId");
        }
        System.out.println("[ProductCouponNotify] step=parse_start, merchantCode="
                + config.getMerchantCode() + ", wechatPayPublicKeyId=" + config.getWechatPayPublicKeyId()
                + ", wechatpaySerial=" + wechatpaySerial);
        try {
            PublicKey wechatpayPublicKey = WechatPayProductCouponCertLoader.loadWechatPlatformPublicKey();
            WXPayBrandUtility.Notification notification = parseNotificationWithConfig(
                    config, wechatpayPublicKey, headers, body);
            System.out.println("[ProductCouponNotify] step=parse_ok, merchantCode="
                    + config.getMerchantCode() + ", notificationId=" + notification.getId()
                    + ", eventType=" + notification.getEventType());
            return notification;
        } catch (Exception ex) {
            System.out.println("[ProductCouponNotify] step=parse_fail, merchantCode="
                    + config.getMerchantCode() + ", reason=" + ex.getMessage());
            throw new NotifyParseException("parse notification failed: " + ex.getMessage(), ex);
        }
    }

    private WXPayBrandUtility.Notification parseNotificationWithConfig(WechatPayMerchantConfig config,
                                                                       PublicKey wechatpayPublicKey,
                                                                       Headers headers, String body) {
        if (Boolean.parseBoolean(System.getProperty("wechatpay.notify.skipTimestampCheck", "false"))) {
            return WXPayBrandUtility.parseNotificationForLocalDebug(
                    config.getApiV3Key(),
                    config.getWechatPayPublicKeyId(),
                    wechatpayPublicKey,
                    headers,
                    body);
        }
        return WXPayBrandUtility.parseNotification(
                config.getApiV3Key(),
                config.getWechatPayPublicKeyId(),
                wechatpayPublicKey,
                headers,
                body);
    }

    private WechatPayMerchantConfig resolveMerchantConfig(String wechatpaySerial) {
        if (!isBlank(wechatpaySerial) && merchantList != null) {
            for (WechatPayMerchantConfig config : merchantList) {
                if (config != null && wechatpaySerial.equals(config.getWechatPayPublicKeyId())) {
                    return config;
                }
            }
        }
        if (merchantList == null || isBlank(defaultMerchantCode)) {
            return null;
        }
        for (WechatPayMerchantConfig config : merchantList) {
            if (config != null && defaultMerchantCode.equals(config.getMerchantCode())) {
                return config;
            }
        }
        return null;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static final class NotifyParseException extends RuntimeException {
        public NotifyParseException(String message) {
            super(message);
        }

        public NotifyParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
