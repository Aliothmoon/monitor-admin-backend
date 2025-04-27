package com.swust.aliothmoon.utils;

import cn.hutool.crypto.digest.DigestUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CryptoUtils {
    public String hashPassword(String pwd) {
        return DigestUtil.md5Hex(pwd);
    }

    public static void main(String[] args) {
        System.out.println(hashPassword("aliothmoon"));
    }
}
