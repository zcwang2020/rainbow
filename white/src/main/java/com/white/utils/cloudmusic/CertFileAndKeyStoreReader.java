package com.white.utils.cloudmusic;


import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class CertFileAndKeyStoreReader {
	
	public static final String CERT_TYPE_X509 = "X.509";

	/**
	 * 从指定的cer文件中获取公钥证书
	 * @param cerPath 存放公钥的证书文件路径
	 * @return 保存公钥的<code>X509Certificate</code>实例
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static X509Certificate getX509CertificateInCertFile(String cerPath)
			throws CertificateException, IOException {
		FileInputStream cerIn = null;
		try {
			CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE_X509);
			cerIn = new FileInputStream(cerPath);
			return (X509Certificate) certificateFactory.generateCertificate(cerIn);
		} finally {
			if (cerIn != null) {
				cerIn.close();
			}
		}
	}
	
	/**
	 * 由密钥库获得指定别名的私钥，未指定别名时则获取第一个私钥
	 * @param keyStorePath KeyStore证书文件路径
	 * @param passwd 证书密码
	 * @param alias 指定的私钥别名
	 * @return 私钥
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 */
    public static PrivateKey getPriKeyInKeyStore(String keyStorePath, String passwd, String alias, String keyStoreType) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
    	FileInputStream is = null;
    	try{
    		//实例化密钥库
    		KeyStore ks = KeyStore.getInstance(StringUtils.isEmpty(keyStoreType) ? KeyStore.getDefaultType() : keyStoreType);
            is = new FileInputStream(keyStorePath);
            //加载密钥库
            ks.load(is, passwd.toCharArray());
            if(StringUtils.isEmpty(alias)){
            	Enumeration<?> aliasEnum = ks.aliases();
            	alias = (String) aliasEnum.nextElement();
                if(!ks.isKeyEntry(alias)){
                    throw new KeyStoreException("pfx文件alias异常...");
                }
            }
            return (PrivateKey) ks.getKey(alias, passwd.toCharArray());
        }finally{
        	if(is != null){
        		is.close();
        	}
        }
    }
    
    /**
     * 由密钥库获得第一个私钥
     * @param keyStorePath 密钥库路径   
     * @param passwd 证书密码  
     * @return 私钥
     * @throws KeyStoreException 
     * @throws IOException 
     * @throws CertificateException 
     * @throws NoSuchAlgorithmException 
     * @throws UnrecoverableKeyException 
     */
    public static PrivateKey getPriKeyInKeyStore(String keyStorePath, String passwd) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
    	return getPriKeyInKeyStore(keyStorePath, passwd, "", "");
    }
    
    public static PrivateKey getPriKeyInKeyStore(String keyStorePath, String passwd, String keyStoreType) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
    	return getPriKeyInKeyStore(keyStorePath, passwd, "", keyStoreType);
    }
    
    public static void main(String[] args) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
    	getPriKeyInKeyStore("d:\\user-123456.pfx", "123456", "PKCS12");
    	getX509CertificateInCertFile("d:\\user-123456.cer");
	}
}
