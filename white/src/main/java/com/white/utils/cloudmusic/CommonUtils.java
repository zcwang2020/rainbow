package com.white.utils.cloudmusic;

import java.util.*;

/**
 * <p>Created by <a href="mailto:dingjun@corp.netease.com">dingjun</a> at 2018-11-21 16:24:56
 *
 * @author dingjun
 */
public class CommonUtils {

    public static final String CHARSET = "UTF-8";

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkStringFromObj(Map<String, Object> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = params.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                content.append(key).append("=").append(value);
            } else {
                content.append(key).append("=").append(value).append("&");
            }
        }
        return content.toString();
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                content.append(key).append("=").append(value);
            } else {
                content.append(key).append("=").append(value).append("&");
            }
        }
        return content.toString();
    }

    public static Map<String, Object> paramsFilterFromObj(Map<String, Object> sArray) {
        Map<String, Object> result = new HashMap<>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            Object value = sArray.get(key);
            if (value == null
                    || value.equals("")
                    || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    public static Map<String, String> paramsFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null
                    || value.equals("")
                    || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    public static String toHex(byte[] byteArray){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            String hex = Integer.toHexString(0xFF & byteArray[i]);
            if (hex.length() == 1) {
                str.append("0").append(hex);
            } else {
                str.append(hex);
            }
        }
        return str.toString();
    }
}
