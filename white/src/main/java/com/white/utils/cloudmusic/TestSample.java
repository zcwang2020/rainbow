package com.white.utils.cloudmusic;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestSample {

    /**
     * 说明：本地测试的时候，用工具生成2048位的密钥，公钥给云音乐方配置好后，替换PriKeyStr变量的值为生成的私钥值，并修改merchant_id和business_id即可进行测试
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        /**
         * MusicPubKeyStr为云音乐测试环境公钥
         * 【生产环境公钥下载地址：https://music.163.com/st/merchant/help】
         * 【生产环境公钥压缩包：music-pub.zip】
         */
        String MusicPubKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";
        //TODO 替换PriKeyStr为自己生成的私钥,与提供给云音乐后台配置的公钥对应
        String PriKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0vMNZZXENZkXnxK14uLt7dh7lUNVZtjrm7eL4m63KK/JLIlOiVgVEeYbw+jIknzOPsHgAZsXijlD0TS4LiXTel5Kyrr9XYeSl2HGVvOzb+t7IL0jIOomYAnh/1sSLBfM+N4faZ70bf/lLGsHxXLe4kCcIQEnrLjWBn9uD3edeG30nmcoUqStIvbktu+3bwD8xCLVSzloH/96/YHUEdM3dT8XZf7FXkl/SHYc1PaMv7+u6p5gioa5ECBKJv5Xi+rf32xji9KCERhJhvbfbg3Qr9wxSC1qpFa/FB0vMoFkkMDh9kpzcN0ff53bCqrmrl0CPWJITFLL5gtsxRtmpJgjhAgMBAAECggEBAK0KVRYQE286zg8atONyKr4H14jCHaJo4HgcpkRT+M4pyS66TMyOAzkOCW5KfRum+NzHnfz0aN2CWGuuQTl2f1hmUmOU/0kQRrOqSQWN/lCXHhkXfF2H/hh1tRzgaQiZx8t3qzAmRl4/5lRL6AfIN3YTPeuecacjdMMIZEkaedwUZGeBZLcu3ntU/gByUqThebVe1SGrHTCiiSyzspdAmis+U4SzJ57RRJ/++OS4Us2LB4a9VFGlpZu3FfFSzz4+ZziSaOVTpEfuuaYx7fM/l446FED6hiGj1ox9HOQKL7FLCLwgdibxgozmGJ0NAVpY/gF8L0PQbAG/lMwIAzygAAECgYEA6CFDYZzSKyxPWiJSHr7ipP6vsaSDajN8X6Ui8qpwysLZB+4ONxEBI54vH2Y2JSuApAxLRpmNa+l/LoU6YMBj6+Y3i4eKaJLmbECsmIOSU0rdtvPbMweu/rzU4e4k/GURg56dftgfI1htPyDsMv9ndmKbeAn+bZHYh0CGtD3LTAECgYEAx1Kb2QgG063/V6ubV94TKVFLwrv50OF26ivi9CU9p+i+X9cZEnlRc56flee3UsdVjCr9vbtFTZm/GznLC7HnCypQ3LJbNLiw4/PtmhRlcbx+xnF5kqm5bpWy5s75RTcwjVThFWbOSBBXKhkZs/RVv4ICOJP0AizLIN6K5dOoPOECgYEAkjOpGcjIwnJlBDMLn9vAKgVTKUKhlnkZA7Cho09gPQSd5xWzlFE8c2+E5HTe82hD3I71tmCuJuJovSciTp++D2zvXQ0gSp8DS/kr+98agSVtIXbTf39+Y2kRd78jQmhxsIFtVt/ONNfT7ufQfIpKYtDWdLNusRoj04P7k3g8HAECgYAW3XUfWnpeAhHOntL9K26LSNYmCqZHvfWEO3CXjUYbYumj2Or5YY1kdAfejGxNRKydnbjp8PesRTzVBLY0Kz5T25DXIGayD5XLMIpLKJfSiksIvK+zkWDCPM3/CDM0K3aYX+pKKDb1sVoubB60tAy6IVHb560QKs9Kf/zzDGq6QQKBgCHM8+l0ADc/LSH4OwMP3Dk5dYsYJMxcjC2zuVzXhyw+49ZadRXwb9dT2cTlSuxyV8cr3/QXJAdEjeAJmevl0FkEz5Ue9wwKyhTFxgwjE3dwzQlzPLK16aK2jMfClcdCNiBsYlKeJT6SU0a5gVGFppdbDYfSC6e7oZcIgzP2Saez";

        Map<String, Object> map = new HashMap<String, Object>();
        //TODO 替换merchant_id
        map.put("merchant_id", "3720789");
        //TODO 替换business_id
        map.put("business_id", "4011302");
        map.put("activity_id", "7451025");
        map.put("key_alias", "vip-sub-key-1");
        map.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        map.put("version", "1");
        //幂等参数，相同orderNo只会扣费一次，会员只会生效一次
        map.put("order_no", "312342794278492144311111");
        map.put("item_code", "redvip1");
        //实际生产环境根据充值情况传数量
        map.put("item_quantity", "1");
        map.put("user_mobile", "13526699210");
        //私钥签名
        map.put("sign", Base64.encodeBase64String(SHA256WithRSA.sign(
                CommonUtils.createLinkStringFromObj(CommonUtils.paramsFilterFromObj(map)).getBytes(CommonUtils.CHARSET),
                RSAUtil.generatePrivateKey(PriKeyStr))));
        System.out.println("map = " + map);
        String url = "https://music.163.com/api/music-vip-open/partner/vip-sub/v2";
        String res = HttpClient.httpPostKeyValueForm("", url, map, "UTF-8", 5000, 5000, 10);
        System.out.println("["+res+"]");
        Map<String, Object> map1 = JSON.parseObject(res, Map.class);
        //云音乐公钥解签
        boolean b = SHA256WithRSA.verify(
                CommonUtils.createLinkStringFromObj(CommonUtils.paramsFilterFromObj(map1)).getBytes(CommonUtils.CHARSET),
                Base64.decodeBase64(map1.get("sign").toString()), RSAUtil.generatePublicKey(MusicPubKeyStr));
        System.out.println(b);
    }
}
