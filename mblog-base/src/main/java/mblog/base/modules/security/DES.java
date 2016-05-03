/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/package mblog.base.modules.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES双向加密/解密类
 * 
 * @author langhsu
 *
 */
public class DES {
	private static final String ALGORITHM = "DES";

	public static String getSecretKey(String seed) throws NoSuchAlgorithmException {
		SecureRandom secureRandom;
		if (seed != null && !"".equals(seed))
			secureRandom = new SecureRandom(seed.getBytes());
		else
			secureRandom = new SecureRandom();
		KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
		keyGenerator.init(secureRandom);
		SecretKey secretKey = keyGenerator.generateKey();
		return Base64.encodeBase64(secretKey.getEncoded());
	}

	/**
	 * DES加密
	 * 
	 * @param data 要加密数据
	 * @param key 密钥
	 * @return 加密后的byte数组
	 * @throws Exception 出错
	 */
	public static byte[] encrypt(byte[] data, String key) throws Exception {
		Key k = toKey(Base64.decodeBase64(key));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	/**
	 * 转换密钥
	 * 
	 * @param key 密钥
	 * @returnKey Key对象
	 * @throws Exception 出错
	 */
	private static Key toKey(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}

	/**
	 * DES解密
	 * 
	 * @param data 要解密的数据
	 * @param key 密钥
	 * @return 解密后的数据
	 * @throws Exception 出错
	 */
	public static byte[] decrypt(byte[] data, String key) throws Exception {
		Key k = toKey(Base64.decodeBase64(key));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}
}
