package com.yunqin.bearmall.util;


import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.util
 * @DATE 2020/1/3
 */
public class RSAUtil {

    // 公钥
    public static String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNEhGrABJU4O0B766Vyh51hpBY" +
            "sdclPDtsElYKXR8Of3wbDNFyicu0Q+RHvUYEK8D594NpLKM79TC49Ayx1ddDyiTB" +
            "GcVBlmboGs/WYVvS/AksUAsWh34wIccxYlzJAb9ABa8GR0oM5JNAv4v5g3jWl9X3" +
            "MpTjMX+r8H1oPdOJ8wIDAQAB";
    // 私钥
    public static String privateKeyStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM0SEasAElTg7QHv" +
            "rpXKHnWGkFix1yU8O2wSVgpdHw5/fBsM0XKJy7RD5Ee9RgQrwPn3g2ksozv1MLj0" +
            "DLHV10PKJMEZxUGWZugaz9ZhW9L8CSxQCxaHfjAhxzFiXMkBv0AFrwZHSgzkk0C/" +
            "i/mDeNaX1fcylOMxf6vwfWg904nzAgMBAAECgYAJL94Kwx4PH2R+ybYQj9V9fmHJ" +
            "F26EBIbE+K1SGYnipGNy8sgPUY0GrmPzss8IMM2cZTJF6/IEJZ/hKU3IScQy+Kzs" +
            "b5qaY6Hb5/x8sdUAFK/M6JssUby2BhViBKKWM/O4HcqvjpFHZD9bLv6A1GmcwijQ" +
            "/8DtDyr3ZEsN2yGOAQJBAPcJO3taz9UfQ+YmfT7gCTXv289l9nL9M5SVepIEat5n" +
            "icNgxpt7Rt1KbFYHBA6XtJ+l0n5wjZdBRppBPKjnA5MCQQDUgwLIWjoa5KFBLt6D" +
            "S32YEjMriX3SJaDt2zWXcTtjWXMppaxhGOQ9GtdYoZLZHQUKiSdxtQEw6Wut6awW" +
            "uBwhAkEA6od2VC8r/w7WvIqv/2Bc/jsAuU75AcRHEvoyyqus9gbgMIARLg2EgpSk" +
            "c6vnex8l2SdpxqZ+linfqJTkzQm9RwJAFB0uVz5K/56iMKQ6BV1QzuLyGu3RjeFn" +
            "4Pt9kA70mwutXcTDkxEjETGILmhkmM4pWvzuUdHbAgEl4vkaA7B7YQJBAKTej1JJ" +
            "1FPYBo0th4+vCttZ4nxgv9pt7TzZwCRMfIqxB2kvT1HlcFFByPZl59/imRLub6vK" +
            "gI9Y04kM4vQBuQs=";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;


    /**
     * 将base64编码后的公钥字符串转成PublicKey实例
     * @param publicKey 公钥字符
     * @return publicKEY
     * @throws Exception exception
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = Base64.decode(publicKey, Base64.NO_WRAP);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 将base64编码后的私钥字符串转成PrivateKey实例
     * @param privateKey 私钥字符串
     * @return 私钥对象
     * @throws Exception exception
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey, Base64.NO_WRAP);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * RSA加密
     * @param content 待加密文本
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception exception
     */
    public static String encrypt(String content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] data = content.getBytes();
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return new String(Base64.encode(encryptedData, Base64.NO_WRAP));
    }

    /**
     * RSA解密
     * @param content 密文
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception exception
     */
    public static String decrypt(String content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedData = Base64.decode(content, Base64.NO_WRAP);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData);
    }

}
