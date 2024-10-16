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
        // String MusicPubKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";

        String MusicPubKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv6tJtziH5sT1XnSu//LKlcKvu3b7H35nHWq6LNIY8JRm621IPqaX58Gr/2IDcflqs1GjpE3QOBm6bSmTOuz10O//F+GgtvJiclUf479EPYYzjB9Ye6CZ+SvYzhOiswKUdxV8gxayb19Q8ZtiMHSbg+AGE02dGXzhBlIS49PMIh7PhTX2LbyjHxsZBuveecxAsLXL7iQPg/CKjR/b1sCp7XpMPKRRFRQWP1/m6mmQSwqjar2hMuKMUkFANKKx8GBXbIdw9qWB3q/faIGSp1+UNa0PzgEYzBUwlcQkANuRqoLWA8JzQr/4Dq0g3CBOMGzu1LANC7HMd8x93D+q8HyVwQIDAQAB";
        //TODO 替换PriKeyStr为自己生成的私钥,与提供给云音乐后台配置的公钥对应

        // String PriKeyStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJJL8OLB0J/9pmzHFxpwOeigamHd3Yk6PkZdaL6reDOdlq5mOQ0/xIqXcnaWI/Q7qtT9j/b34hR74ZMyEw4Um5mbWG0C0qK7l6RbQaUExbF/gU+RiVCQ8TQW1qgw/eBh+H47Aj58hGulbfJKfeZJydzpnvTSdT9VitGR9xIJtKdHAgMBAAECgYBMmbzATnE5RGu+qyP6sOZxWoU5Rx03PCrdVw2AQHIIvKvoFxgqSshTNOc3Fngu6osRSM73pmVXCmJbWy3FAp9Rqg2FZfQoX+ds4cnj3QVpeILw6b2Sr0rI2OBkbXGFre/crM+JcjYBAkV7pnwcWRH3EyOvzLUqKs5qEkOycxTi8QJBAOUFVS8ipCnp7Qaynig6PcfJC0JP4GxpFmQu0w1OrmlzP/zezUfRwihTx1NPssJm9HD7KNiBDlgFj0PQJkGbB18CQQCjh90kBAoloAsCxe/qD4w7lbre75P16Kicb+K0FCeJsZrdXpApFhlDo60zPNUJEPph9HFptZfNBE8I8dIesHEZAkEAxe4V8Oa/ennxoBg/GAU936yhTm46R3eLIopVXOrjUb+JTcJBKBDg/Hlri1UV6W2RVRO7+WGQRAKKDtGWPpz9gQJAImZAFIVtBQEnj8vHbfsbSqVyi9blzwLEBTRcAfmDX6mmpA5yUNI/OkVB99dCEQgrQ1PCT7RNXGkdnwoPYzlGcQJBAJQQrWM8SxovyqcN7Md2wRvIjA1Ny7OJGSR8y+0eu/D0GydQbUj1rNdPX5CLNFVwvcgMwkLNUD+u+JSol5+PQHk=";
        String PriKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDANuW/DWNADTjgNBgZssW0UvU+Q4CsqfwQH/9x7rLhCwdx7QugxWelwgFLQUrlnDqnxrdJMLvJSX8VKNUoLl8QCvdcfLgv0wkGasOHWFH+e/2lDB5N/fQHFwxl3LyzyJeYHGnAP0PyovByJT0Sb8zEv4vBOh5frNC0191EJbP95xUppfeBBG8+wXDk1gu/S12XXOZ3EWvtLjjqEDDQAFLTa+MFfIgBuMuVVoSh0LcmXpROLwrZ0UJDbsjIU2M3zBabfBDEoTGf4/crMjZEtEzzwAJUzBIC7/c0vefCwpOILkpiM3vUTnp6w6POoAbi5orW4+UwcNgr3uG1YAu4eV7VAgMBAAECggEATEd89UzCznBw2cjSp6AihWVGneb78Ymj1mJcZUJtCpHt6nA/5RVyBH3xNYQ7zNMrE9nwcsM8Qq1AGY2SydddMminsbRtA82kDz/fSJ7sKynZht5ASIM3jHjBGst2t4IrZtofvYNd27R9506P5fkoMgwx0gTWePGEAcCItrqfq2qQ6TBBDnn84PiFQ2MO08HT8Mq4k2FT1iO+WefIFUMgBluU3sEJgBuqjNl6/hTRVQiCyTC148tfP6qwupQMorlaH7pubF4RulvrxjAC8bLTdPXJG1AElQvd+TB/r75MhRGKEuKUpX/eXJ/iCI/TO/cZTha/apes/DhD/UzxTQiGAQKBgQDnmfk6uT7ykF9wz8l212DGmXTWOxIfpMarH2TGpXHQWB+lW6atr19wr83buSboo1+0/qiBk1nSY5pf76byXRlu1HXSS4CFL7eoUkDhUtyGqusU8kUZTJEFoMdVCgZvwJylZic6fb5xUUjcKmkkoxjjagNMar8Ly1SFVTNno2uNhQKBgQDUdrN8xe4qSOf2g/9o0gTQm30JFlaiRxHp8kv0yVqQj/jlmoAs/h6p85bqCQeE3e2M2Q8Cp9yYKW/tUoYZicSPv+R7CjXf7uWdBT0m8NdUgAUPPAKGAoNLTCmrhh8otRU8Xh5K3ZDdLZd0zB6r9OTU97RJsvZPhycPs7ax19nlEQKBgQDlPL43pcvsg4I0QzcB7kTXzmSa93UE5S/PiQ9Sn8d7lP4dyg28tG0fOKPl6+nbMno0yCs5tgOd/wB6HNd95TCbRqgoMptPON/sNx4I6N9mXB67aOGEHQkeLPmf7oBYdKmX/PivKG8r8mzPnxrbLKQh1HjXVoEqx2MtZhuvZU27kQKBgDqPvUlXBR6Cme1I1M1/lfQp53zpu1dfX4CsWGNQXLYb+O7QdyPvp2QQ0DgR5RCqWx/HdGu2+Qa35TH03SGM57D3o3KGjfL7Vi7Bkul42OFhqANa9xcY+jd29sVgYpZF02SgTJk2GQfd2TFIUevzxF8vJ3Jo8ZtaFVountzGMjkBAoGBAJ9KWXmOCm5ugSlb4iqh7VF0W/FkqE1RmUMDK4iQc4DPMIg5gS+IC+mp78b1CHDFp3fhdtLDxK02txxyHW0gukOQvL1JLu0KxBPCYbjZj0FH6W0RAV6/vi4+dXXnYzLRH5dz5vcDwwiqGHRUOK8ooxf7abMWE+7itM/Nvao/7PHU";

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
        map.put("order_no", "31234279427849214431");
        map.put("item_code", "redvip1");
        //实际生产环境根据充值情况传数量
        map.put("item_quantity", "1");
        map.put("user_mobile", "17772162284");
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
