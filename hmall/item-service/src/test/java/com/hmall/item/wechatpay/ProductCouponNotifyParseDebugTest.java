package com.hmall.item.wechatpay;

import com.alibaba.fastjson.JSON;
import okhttp3.Headers;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 严选商品券回调本地调试（rainbow/item-service）。
 * 仅保留跳过 5 分钟时间戳、仍验签+解密的用例。
 */
public class ProductCouponNotifyParseDebugTest {

    private static final String API_V3_KEY = "";
    private static final String WECHAT_PAY_PUBLIC_KEY_ID =
            "";

    private static final String NOTIFY_BODY =
            "{\"id\":\"3fd537f2-56a8-5bad-bda8-aa8c33d4f4a2\",\"create_time\":\"2026-05-19T20:39:37+08:00\","
                    + "\"resource_type\":\"encrypt-resource\",\"event_type\":\"PRODUCT_COUPON.SEND\","
                    + "\"summary\":\"商品券领券通知\",\"resource\":{\"original_type\":\"product_coupon\","
                    + "\"algorithm\":\"AEAD_AES_256_GCM\",\"ciphertext\":\"Rw3Rn5xhqHaTIwvCiCJ56TK7EAJGFYEo/NdIBVWkScQyDlOXfOHsDZSzftPz53ZoRN8NzLcoDxzlPp2mvtay0P8Mt7P5AHjBNw3tapxGOSVBB9iN3LjXyBKSWOH3mwK62WD5q4TVpgvw2PxlMIf7R+KY+thPnRMemw9nZ/pccrHsQHCOJs1HGpHj/583yo4ZgSE6ZgqYWPOk4xggRDFv1W4v1BRn6EDV5eRLDXaZmfLkQ5HCzod3mdF5QuT0kE0aHGKKWfaamjtpEHiocnLrU+sQd+1rUmGQ8iKRHUFLfrQF2eb1G7pLcPkBZHpH42+GYS0AudfsSTSwcWlpmxmt/n/yx95gJjDwPwejQQSVPvwpx0QsCs86jVtENoMB3xQ2+NAFr3ee5d+FoXAXNPfLBNCWGjrv7CqG7WcC1zIxrgFLcwCmeUy909HWyfTeRm2oTUDUXc0B7ZF2xslGxqAZBk327T3uKcwMsrMT9HJgzAZ9st8Y7vVuUD2yr1/KZlBh5/RrRGIFhTxxOvjCsxSugcDSYRFnNrGJ8w+NOF3xYqcnaXtDSkuB+UcYeTk3XBkluT2mN4pSsNfzXGAkN+vsXV3TERpSRxC1HXtU4M+aNF55ngAXkQ24rm302qo+PDR3UtKKWl4bOmLnnmZ/HWXMbwAYyaakkzQOEIa5eNDZr7jgrxGzawbc3XfXJMqVagv17S0N1vOa2RUkCoL1lfP5vMezuqZQ+FLhoA==\","
                    + "\"associated_data\":\"product_coupon\",\"nonce\":\"RyEAOXA83YES\"}}";

    private static final String WECHATPAY_TIMESTAMP = "1779225770";
    private static final String WECHATPAY_NONCE = "";
    private static final String WECHATPAY_SIGNATURE =
            "YNFzf7pNYteV08Gypn+rh8d7PalqRcbqCMYfKZM/3ZMRj9Tlfho1PLu24qDIW5J9bMCRKB2FeF8YZ3aUB5nWmdeXQgPL9J9TznAU9jnpChb0+zSq8W1jrXtSM8IS/7XfAcgOUTU9i7cuSMbnCVlCoyV6ZvVl4TC2v7miW4JpI+yOrOAfRwWvs8o3zmkaUSYgWVOVzESCEdbo0gqrEOvvtStX0KFr1Bj+4eQscI9CtsnmtQohD4wA8iP0tUtsrK2gTDiAdVKPOTt9nPKAzija6AbWo6Jj4CtnHl7HNeU2TXhb20q+uuD/UWUM1fjYgrvtwGkGzoE60MMjlJDtYHx25w==";

    private ProductCouponNotifyParseSupport parseSupport;

    @BeforeMethod
    public void setUp() {
        System.setProperty("wechatpay.notify.skipTimestampCheck", "true");
        parseSupport = ProductCouponNotifyParseSupport.forMch001(API_V3_KEY, WECHAT_PAY_PUBLIC_KEY_ID);
    }

    @AfterMethod
    public void tearDown() {
        System.clearProperty("wechatpay.notify.skipTimestampCheck");
    }

  /** 对齐严选 parseNotificationOrThrow；跳过时间戳，仍验签并解密。 */
    @Test
    public void parseNotification_full_skipTimestamp() {
        WXPayBrandUtility.Notification notification =
                parseSupport.parseNotificationOrThrow(buildNotifyHeaders(), NOTIFY_BODY);
        Assert.assertEquals(notification.getId(), "3fd537f2-56a8-5bad-bda8-aa8c33d4f4a2");
        Assert.assertNotNull(notification.getPlaintext());
        System.out.println("[ProductCouponNotifyParseDebug] notification=" + JSON.toJSONString(notification));
    }

    private static Headers buildNotifyHeaders() {
        return new Headers.Builder()
                .add("Wechatpay-Timestamp", WECHATPAY_TIMESTAMP)
                .add("Wechatpay-Nonce", WECHATPAY_NONCE)
                .add("Wechatpay-Serial", WECHAT_PAY_PUBLIC_KEY_ID)
                .add("Wechatpay-Signature", WECHATPAY_SIGNATURE)
                .add("Wechatpay-Signature-Type", "WECHATPAY-BRAND-SHA256-RSA2048")
                .build();
    }
}
