package cn.jeeweb.web.utils;

import org.apache.shiro.codec.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.web.utils
 * @title:
 * @description: 密钥生成java代码
 * @author: 王存见
 * @date: 2018/3/2 9:13
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
public class ShiroCipherKeyGenerator {

    public static void main(String[] args) {
        KeyGenerator keygen = null;
        try {
            keygen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecretKey deskey = keygen.generateKey();
        System.out.println(Base64.encodeToString(deskey.getEncoded()));
    }
}
