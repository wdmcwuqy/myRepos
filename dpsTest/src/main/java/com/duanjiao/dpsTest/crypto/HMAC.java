package com.duanjiao.dpsTest.crypto;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * HMAC(Hash Message Authentication Code，
 * 散列消息鉴别码，基于密钥的Hash算法的认证协议。
 * 消息鉴别码实现鉴别的原理是，用公开函数和密钥产生一个固定长度的值作为认证标识，用这个 标识鉴别消息的完整性。
 * 使用一个密钥生成一个固定大小的小数据块，即MAC，并将其加入到消息中，
 * 然后传输。接收方利用与发送方共享的密钥进行鉴别认证 等
 * @author Administrator
 *
 */
public class HMAC {
	
	private static final String KEY_MAC="ASDFGHJKLZXCVBNMQWERTYUIOP";
	
	/**
     * 初始化HMAC密钥
     * 
     * @return
     * @throws Exception
     */
    public static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.encryptBASE64(secretKey.getEncoded());
    }
 
    /**
     * HMAC加密
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(Base64.decryptBASE64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
 
    }
}
