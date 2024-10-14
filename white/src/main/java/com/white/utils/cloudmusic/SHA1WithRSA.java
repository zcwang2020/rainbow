package com.white.utils.cloudmusic;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class SHA1WithRSA {
	
	private static final String SHA1_WITH_RSA = "SHA1withRSA";

    /**
     * SHA1withRSA加签名
     * @param sign
     * @param keyStorePath
     * @param passwd
     * @return
     * @throws CertificateException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException
     * @throws KeyStoreException
     * @throws SignatureException
     * @throws InvalidKeyException
     */
    public static byte[] sign(byte[] sign, String keyStorePath, String passwd) throws CertificateException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, SignatureException, InvalidKeyException{
        Signature signature = Signature.getInstance(SHA1_WITH_RSA);
        //初始化签名
        signature.initSign(CertFileAndKeyStoreReader.getPriKeyInKeyStore(keyStorePath, passwd));
        signature.update(sign);
        return signature.sign();
    }

    /**
     * SHA1WithRSA加签名
     * @param sign
     * @param priKey
     * @return
     * @throws CertificateException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException
     * @throws KeyStoreException
     * @throws SignatureException
     * @throws InvalidKeyException
     */
    public static byte[] sign(byte[] sign, PrivateKey priKey) throws CertificateException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, SignatureException, InvalidKeyException{
        Signature signature = Signature.getInstance(SHA1_WITH_RSA);
        //初始化签名
        signature.initSign(priKey);
        signature.update(sign);
        return signature.sign();
    }
    
    /**
     * 
     * @param sign
     * @param keyStorePath
     * @param passwd
     * @param alias
     * @return
     * @throws CertificateException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException
     * @throws KeyStoreException
     * @throws SignatureException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] sign(byte[] sign, String keyStorePath, String passwd, String alias) throws CertificateException, 
    		IOException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, SignatureException, 
    		InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        Signature signature = Signature.getInstance(SHA1_WITH_RSA);
        //初始化签名
        signature.initSign(CertFileAndKeyStoreReader.getPriKeyInKeyStore(keyStorePath, passwd, alias));
        signature.update(sign);
        return signature.sign();
    } 
    
    /**
     * SHA1WithRSA验证
     * @param data
     * @param sign
     * @param cerPath
     * @return
     * @throws CertificateException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verify(byte[] src, byte[] sign, String cerPath) throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        //构建签名
        Signature signature = Signature.getInstance(SHA1_WITH_RSA);
        signature.initVerify(CertFileAndKeyStoreReader.getX509CertificateInCertFile(cerPath));
        signature.update(src);
        return signature.verify(sign);
    }
    
    /**
     * 
     * @param src
     * @param sign
     * @param pubKey
     * @return
     * @throws CertificateException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verify(byte[] src, byte[] sign, PublicKey pubKey) throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        //构建签名
        Signature signature = Signature.getInstance(SHA1_WITH_RSA);
        signature.initVerify(pubKey);
        signature.update(src);
        return signature.verify(sign);
    }
     
}
