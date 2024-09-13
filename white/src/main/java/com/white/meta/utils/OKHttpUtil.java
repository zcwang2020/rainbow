package com.white.meta.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.white.meta.constant.JstConstant;
import com.white.meta.enums.JstInterfaceEnum;
import com.white.meta.request.JstResponse;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class OKHttpUtil {

    private static Logger logger = LoggerFactory.getLogger(OKHttpUtil.class);

    private static OkHttpClient okHttpClient;

    static {
        okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public static String jstPost(JstInterfaceEnum type, Map<String, String> param) {
        try {
            String result = post(String.format(JstConstant.REQUEST_URL, type.getUrl()), param);
            if (StringUtils.isBlank(result)) {
                logger.error("jst request {} result blank", JSON.toJSONString(type));
                return null;
            }

            JstResponse res = JSON.parseObject(result, JstResponse.class);
            if (res.isSuccess()) {
                return res.getData().toString();
            } else{
                logger.error("jst request {} error res {}", JSON.toJSONString(type), result);
                return null;
            }
        }catch (Exception e) {
            logger.error("jst request {} error {}", JSON.toJSONString(type), e);
        }
        return null;
    }

    public static String post(String url, Map<String, String> param) throws IOException {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        param.forEach((k, v) -> {
            bodyBuilder.add(k, v);
        });
        Request request = new Request.Builder()
                .url(url)
                .post(bodyBuilder.build())
                .header("Content-Type", JstConstant.HTTP_MEDIA_TYPE)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return StringUtils.toEncodedString(response.body().bytes(), StandardCharsets.UTF_8);
    }

    public static byte[] post(String url, byte[] data) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, data);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.body().bytes();
        }
    }

    public static byte[] post(String url, Map<String, String> headers, byte[] data) throws IOException {
        Request.Builder requestBuilder = new Request.Builder();

        requestBuilder.url(url);

        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                String name = header.getKey();
                String value = header.getValue();
                requestBuilder.header(name, value);
            }
        }

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, data);
        requestBuilder.post(body);

        Request request = requestBuilder.build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.body().bytes();
        }
    }

}
