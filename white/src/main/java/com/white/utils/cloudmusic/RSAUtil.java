package com.white.utils.cloudmusic;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {

	//private static final String TRANSFORMATION = "RSA/ECB/OAEPWithSHA1AndMGF1Padding";
	private static final String CHARSET = CommonUtils.CHARSET;
	private static final String TRANSFORMATION = "RSA/ECB/NoPadding";
	/**
	 * 根据RSA私钥串生成<code>PrivateKey</code>对象
	 * @param priKey
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static PrivateKey generatePrivateKey(byte[] priKey) 
			throws InvalidKeySpecException, NoSuchAlgorithmException{
		return KeyFactory.getInstance("RSA").generatePrivate(
				new PKCS8EncodedKeySpec(priKey));
	}

	public static PrivateKey generatePrivateKey(String base64PriKeyStr)
			throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
		return generatePrivateKey(Base64.decodeBase64(base64PriKeyStr.getBytes(CHARSET)));
	}
	
	/**
	 * 根据RSA公钥串生成<code>PublicKey</code>对象
	 * @param pubKey
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static PublicKey generatePublicKey(byte[] pubKey) 
			throws InvalidKeySpecException, NoSuchAlgorithmException{
		return KeyFactory.getInstance("RSA").generatePublic(
				new X509EncodedKeySpec(pubKey));
	}

	public static PublicKey generatePublicKey(String base64PubKeyStr)
			throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
		return generatePublicKey(Base64.decodeBase64(base64PubKeyStr.getBytes(CHARSET)));
	}
	
	/**
	 * RSA私钥解密
	 * @param cryptograph 密文数据
	 * @param privateKey 私钥对象
	 * @return 解密后的明文
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] cryptograph, PrivateKey privateKey) 
			throws Exception {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, privateKey); // 解密
		return cipher.doFinal(cryptograph);
	}
	
	/**
	 * RSA公钥加密
	 * @param msg 待加密明文
	 * @param publicKey 公钥对象
	 * @return 加密后密文
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] msg, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey); // 加密
		return cipher.doFinal(msg);
	}
}
