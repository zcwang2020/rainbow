package com.white.utils.cloudmusic;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

public class MD5WithRSA {

	public static final String MD5_WITH_RSA = "MD5withRSA";
	
	//public static final String SHA1_WITH_RSA = "SHA1withRSA";

	public static byte[] signDataByMD5WithRSA(byte[] signData, PrivateKey privateKey)
			throws Exception {
		// 构建签名
		Signature signature = Signature.getInstance(MD5_WITH_RSA);
		signature.initSign(privateKey);
		signature.update(signData);
		return signature.sign();
	}

	/**
	 * 对指定字节数组做MD5WithRSA签名验证
	 *
	 * @param src
	 *            待验证的字节数组
	 * @param sign
	 *            签名的字节数组
	 * @param pk
	 *            公钥，<code>PublicKey</code>实例
	 * @return 当验证成功返回<code>true</code>；当验证失败返回<code>false</code>
	 * @throws IOException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static boolean verifyDataViaMD5WithRSA(byte[] src, byte[] sign, PublicKey pk)
			throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		// 构建签名
		Signature signature = Signature.getInstance(MD5_WITH_RSA);
		signature.initVerify(pk);
		signature.update(src);
		return signature.verify(sign);
	}

	/**
	 * 对指定字节数组做MD5WithRSA签名验证
	 *
	 * @param src
	 *            待验证的字节数组
	 * @param sign
	 *            签名的字节数组
	 * @param cerPath
	 *            存放公钥的cer文件路径
	 * @return 当验证成功返回<code>true</code>；当验证失败返回<code>false</code>
	 * @throws IOException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static boolean verifyDataViaMD5WithRSA(byte[] src, byte[] sign,
												  String cerPath) throws IOException, CertificateException,
			NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		// 获取公钥
		PublicKey pk = CertFileAndKeyStoreReader.getX509CertificateInCertFile(
				cerPath).getPublicKey();
		// 构建签名
		return verifyDataViaMD5WithRSA(src, sign, pk);
	}

	/**
	 * 对文件做MD5WithRSA签名验证。
	 * <p>
	 * 由于该方法会将文件读取到内存，须考虑文件过大而占用内存过多对性能的影响。
	 * 
	 * @param fileName
	 *            待验证的文件
	 * @param sign
	 *            待验证的文件签名数据
	 * @param cerPath
	 *            公钥证书路径
	 * @return 当验证成功返回<code>true</code>；当验证失败返回<code>false</code>
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws SignatureException
	 * @throws CertificateException
	 * @throws InvalidKeyException
	 */
	public static boolean verifyFileViaMD5WithRSA(String fileName, byte[] sign,
			String cerPath) throws NoSuchAlgorithmException, IOException,
			SignatureException, CertificateException, InvalidKeyException {
		// 进行签约校验
		return verifyDataViaMD5WithRSA(readFile(fileName), sign, cerPath);
	}

	/**
	 * 对文件做MD5WithRSA签名验证。
	 * <p>
	 * 由于该方法会将文件读取到内存，须考虑文件过大而占用内存过多对性能的影响。
	 * 
	 * @param fileName
	 *            待验证的文件
	 * @param sign
	 *            待验证的文件签名数据
	 * @param pk
	 *            公钥，<code>PublicKey</code>实例
	 * @return 当验证成功返回<code>true</code>；当验证失败返回<code>false</code>
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws SignatureException
	 * @throws CertificateException
	 * @throws InvalidKeyException
	 */
	public static boolean verifyFileViaMD5WithRSA(String fileName, byte[] sign,
			PublicKey pk) throws NoSuchAlgorithmException, IOException,
			SignatureException, CertificateException, InvalidKeyException {
		return verifyDataViaMD5WithRSA(readFile(fileName), sign, pk);

	}

	private static byte[] readFile(String fileName) throws FileNotFoundException, IOException {
		FileInputStream fin = null;
		ByteArrayOutputStream fout = null;
		try {
			// 将文件内容转化成byte数组
			fin = new FileInputStream(new File(fileName));
			fout = new ByteArrayOutputStream();
			byte[] buff = new byte[fin.available()];
			int len = -1;
			while ((len = fin.read(buff)) != -1) {
				fout.write(buff, 0, len);
				fout.flush();
			}
			return fout.toByteArray();
		} finally {
			if (fin != null) {
				fin.close();
			}
			if (fout != null) {
				fout.close();
			}
		}
	}

	/*public static void main(String[] args) throws Exception {
		// d41d8cd98f00b204e9800998ecf8427e
		String ss = "73e9dbe2dee45e3f96259e2a639f29147600e62b08b160517b9c978d1ff4789de101c18d951a48b1e5f761e4116ca8d31bbb36a9426ad3977840700643738b06a16c15838354dd720312a614727145c4a29a9a51ebaedc07487589e26f51cfb3f208e90b68e4f1f91eb1052dc5e5346ad4527d632e78316b248a305dcbd5c47c";
		// ss = Base64.decode(ss);
		*//*
		 * byte[] bs = decryptByPublicKey(Hex.decodeHex(ss.toCharArray()),
		 * "d:\\server.cer"); System.out.println(Hex.encodeHex(bs));
		 *//*
		boolean bbbb = verifyFileViaMD5WithRSA("d:\\CCF_20130901_2.zip",
				Hex.decodeHex(ss.toCharArray()), "d:\\server.cer");
		System.out.println(bbbb);
	}*/
}
