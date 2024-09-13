package com.white.meta.constant;

import java.nio.charset.StandardCharsets;

public class JstConstant {

    public static final String appSecret = "2f7650f86558454dbf86e6e089450d33"; // 可替换为您的应用的appSecret

    public static final String SIGN_METHOD_MD5 = "md5";

    public static final String HTTP_MEDIA_TYPE = "application/x-www-form-urlencoded;charset=" + StandardCharsets.UTF_8;

    public static final String REQUEST_URL = "https://openapi.jushuitan.com" + "%s";
}
