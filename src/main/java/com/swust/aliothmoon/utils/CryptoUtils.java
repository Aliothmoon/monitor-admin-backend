package com.swust.aliothmoon.utils;

import cn.hutool.crypto.digest.DigestUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CryptoUtils {
    public String hashPassword(String pwd) {
        return DigestUtil.md5Hex(pwd);
    }

    /**
     * 验证密码是否正确
     * @param plainPassword 明文密码
     * @param hashedPassword 哈希后的密码
     * @return 是否匹配
     */
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return hashPassword(plainPassword).equals(hashedPassword);
    }

    public static void main(String[] args) {
        System.out.println(hashPassword("aliothmoon"));
    }
}
