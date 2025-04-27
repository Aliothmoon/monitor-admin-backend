package com.swust.aliothmoon.utils;

import cn.hutool.crypto.digest.DigestUtil;
import com.github.ksuid.KsuidGenerator;
import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class CryptoUtils {
    public String hashPassword(String pwd) {
        return DigestUtil.md5Hex(pwd);
    }

    public static void main(String[] args) {
        System.out.println(hashPassword("aliothmoon"));
    }
}
