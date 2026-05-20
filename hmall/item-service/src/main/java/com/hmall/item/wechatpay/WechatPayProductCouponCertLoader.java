package com.hmall.item.wechatpay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;

/**
 * 对齐严选 {@code WechatPayProductCouponCertLoader}，从 classpath 读微信平台公钥。
 */
public final class WechatPayProductCouponCertLoader {

    private static final String[] WECHAT_PUBLIC_KEY_PEM_PATHS = {
            "certs/wechatpay/wechatpay_public_key.pem",
            "certs.wechatpay/wechatpay_public_key.pem"
    };

    private WechatPayProductCouponCertLoader() {
    }

    public static PublicKey loadWechatPlatformPublicKey() {
        String pem = readClasspathPemFirst(WECHAT_PUBLIC_KEY_PEM_PATHS);
        return WXPayBrandUtility.loadPublicKeyFromString(pem);
    }

    private static String readClasspathPemFirst(String... paths) {
        for (String path : paths) {
            InputStream in = WechatPayProductCouponCertLoader.class.getClassLoader().getResourceAsStream(path);
            if (in != null) {
                try (InputStream input = in) {
                    return readUtf8(input);
                } catch (IOException e) {
                    throw new UncheckedIOException("read classpath pem failed: " + path, e);
                }
            }
        }
        throw new IllegalStateException("classpath 缺少 wechatpay_public_key.pem，请放到 "
                + "src/main/resources/certs/wechatpay/ 或 certs.wechatpay/");
    }

    private static String readUtf8(InputStream in) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        byte[] temp = new byte[4096];
        int n;
        while ((n = in.read(temp)) != -1) {
            buf.write(temp, 0, n);
        }
        return buf.toString(StandardCharsets.UTF_8.name());
    }
}
