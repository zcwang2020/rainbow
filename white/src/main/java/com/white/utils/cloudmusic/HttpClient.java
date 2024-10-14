package com.white.utils.cloudmusic;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 支持HTTP和HTTPS的通讯工具，需要HttpClient 4.5.1版本支持。
 * <p>Created by <a href="mailto:dingjun@corp.netease.com">dingjun</a> at 2018-10-09 16:44:07
 *
 * @author dingjun
 */
public class HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);

    private static final int DEFAULT_CONNECTION_TIMEOUT = 3 * 1000;
    private static final int DEFAULT_SO_TIMEOUT = 20 * 1000;
    private static final int DEFAULT_CONN_MANAGER_TIMEOUT = 1000;
    private static final int DEFAULT_POOL_SIZE = 200;

    private static final String LOCAL_LOCK = "lock";

    private static volatile PoolingHttpClientConnectionManager cm;

    private static CloseableHttpClient getHttpClient(int connTimeout, int connMgrTimeout, int soTimeout, int poolSize)
            throws NoSuchAlgorithmException, KeyManagementException{
        if (cm == null) {
            synchronized (LOCAL_LOCK) {
                if (cm == null) {
                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(null, new TrustManager[] {
                            new X509TrustManager() {

                                @Override
                                public X509Certificate[] getAcceptedIssuers() {
                                    return null;
                                }

                                @Override
                                public void checkServerTrusted(X509Certificate[] chain, String authType)
                                        throws CertificateException {
                                }

                                @Override
                                public void checkClientTrusted(X509Certificate[] chain, String authType)
                                        throws CertificateException {
                                }
                            }
                    }, null);
                    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.INSTANCE)
                            .register("https", new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE)).build();
                    cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
                    if(poolSize == 0){
                        poolSize = DEFAULT_POOL_SIZE;
                    }
                    cm.setMaxTotal(poolSize);// 整个连接池最大连接数
                    cm.setDefaultMaxPerRoute(poolSize);// 每路由最大连接数，默认值是2
                }
            }
        }
        return HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(initConfig(connTimeout, connMgrTimeout, soTimeout)).build();
    }

    private static RequestConfig initConfig(int connTimeout, int connMgrTimeout, int soTimeout) {
        if(connTimeout == 0){
            connTimeout = DEFAULT_CONNECTION_TIMEOUT;
        }
        if(connMgrTimeout == 0){
            connMgrTimeout = DEFAULT_CONN_MANAGER_TIMEOUT;
        }
        if(soTimeout == 0){
            soTimeout = DEFAULT_SO_TIMEOUT;
        }
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connMgrTimeout) // 连接超时
                .setConnectionRequestTimeout(connTimeout) // 获取连接池超时
                .setSocketTimeout(soTimeout).build(); // 读取超时
        return requestConfig;
    }

    public static String httpPost(String logStr, String url, String msg, String charset, int connTimeout, int soTimeout, int poolSize)
            throws Exception {
        return httpPost(logStr, url, msg, charset, connTimeout, soTimeout, poolSize, "application/xml");
    }

    public static String httpPost(String logStr, String url, String msg, String charset, int connTimeout, int soTimeout, int poolSize, String contentType)
            throws Exception {
        HttpPost post = null;
        CloseableHttpResponse res = null;
        try{
            post = new HttpPost(url);
            post.setEntity(new StringEntity(msg, ContentType.create(contentType, Charset.forName(charset))));
            long start = System.currentTimeMillis();
            res = getHttpClient(connTimeout, connTimeout, soTimeout, poolSize).execute(post);
            logger.info("{}访问[{}]HTTP(S)响应码[{}]交互耗时[{}ms]", logStr, url, res.getStatusLine().getStatusCode(), System.currentTimeMillis() - start);
            if (res.getStatusLine().getStatusCode() == 200) {
                HttpEntity resEntity = res.getEntity();
                return (resEntity == null) ? StringUtils.EMPTY : EntityUtils.toString(resEntity, charset);
            }else{
                throw new Exception("HTTP(S)响应码异常:" + res.getStatusLine().getStatusCode());
            }
        } catch(UnknownHostException e) {
            logger.warn("{}HTTP(S)交互异常", logStr, e);
            throw new Exception("HTTP(S)域名解析失败");
        } catch(ConnectTimeoutException e) {
            logger.warn("{}HTTP(S)交互异常", logStr, e);
            throw new Exception("HTTP(S)连接异常");
        } catch (Exception e) {
            throw e;
        }finally{
            if(res != null){
                res.close();
            }
            if(post != null){
                post.releaseConnection();
            }
        }
    }

    public static String httpPostKeyValueForm(String logStr, String url, Map<String, Object> params, String charset, int connTimeout, int soTimeout, int poolSize)
            throws Exception{
        HttpPost post = null;
        CloseableHttpResponse res = null;
        try {
            // post请求
            post = new HttpPost(url);
            if (params != null && params.size() > 0) {
                List<NameValuePair> nvp = new ArrayList<NameValuePair>(params.size());
                for (Entry<String, Object> entry : params.entrySet()) {
                    nvp.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvp, charset);
                post.setEntity(entity);
            }
            long start = System.currentTimeMillis();
            res = getHttpClient(connTimeout, connTimeout, soTimeout, poolSize).execute(post);
            logger.info("{}访问[{}]HTTP(S)响应码[{}]交互耗时[{}ms]",
                    logStr, url, res.getStatusLine().getStatusCode(), System.currentTimeMillis() - start);
            if (res.getStatusLine().getStatusCode() == 200) {
                HttpEntity resEntity = res.getEntity();
                return (resEntity == null) ? StringUtils.EMPTY : EntityUtils.toString(resEntity, charset);
            }else{
                throw new Exception("HTTP(S)响应码异常:" + res.getStatusLine().getStatusCode());
            }
        } catch(UnknownHostException e) {
            logger.warn("{}HTTP(S)交互异常", logStr, e);
            throw new Exception("HTTP(S)域名解析失败");
        } catch(ConnectTimeoutException e) {
            logger.warn("{}HTTP(S)交互异常", logStr, e);
            throw new Exception("HTTP(S)连接异常");
        } catch (Exception e) {
            logger.warn("{}HTTP(S)交互异常", logStr, e);
            throw e;
        } finally {
            if (res != null) {
                res.close();
            }
            if (post != null) {
                post.releaseConnection();
            }
        }
    }

    public static String httpPostParamMap2String(String logStr,  String url, Map<String, Object> params, String charset, int connTimeout, int soTimeout, int poolSize)
            throws Exception{
        HttpPost post = null;
        CloseableHttpResponse res = null;
        try {
            // post请求
            post = new HttpPost(url);
            StringBuilder s = new StringBuilder();
            if (null != params && params.size() > 0) {
                for (Entry<String, Object> entry : params.entrySet()) {
                    s.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
            }
            post.setEntity(new StringEntity(s.substring(0, s.length() - 1).toString(), charset));
            long start = System.currentTimeMillis();
            res = getHttpClient(connTimeout, connTimeout, soTimeout, poolSize).execute(post);
            logger.info("{}访问[{}]HTTP(S)响应码[{}]交互耗时[{}ms]",
                    logStr, url, res.getStatusLine().getStatusCode(), System.currentTimeMillis() - start);
            if (res.getStatusLine().getStatusCode() == 200) {
                HttpEntity resEntity = res.getEntity();
                return (resEntity == null) ? StringUtils.EMPTY : EntityUtils.toString(resEntity, charset);
            }else{
                throw new Exception("HTTP(S)响应码异常:" + res.getStatusLine().getStatusCode());
            }
        } catch(UnknownHostException e) {
            logger.warn("{}HTTP(S)交互异常", logStr, e);
            throw new Exception("HTTP(S)域名解析失败");
        } catch(ConnectTimeoutException e) {
            logger.warn("{}HTTP(S)交互异常", logStr, e);
            throw new Exception("HTTPS连接异常");
        } catch (Exception e) {
            logger.warn("{}HTTP(S)交互异常", logStr, e);
            throw e;
        } finally {
            if (res != null) {
                res.close();
            }
            if (post != null) {
                post.releaseConnection();
            }
        }
    }
}