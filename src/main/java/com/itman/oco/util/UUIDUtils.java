package com.itman.oco.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

/**
 * Created by furongbin on 17/4/22.
 */
public class UUIDUtils {
    private static Random random = new Random();

    public static String md5(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
        }
        byte[] bytes = md.digest(str.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b: bytes) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

    public static String uuid() {
        return UUID.randomUUID().toString().toLowerCase().replace("-", "");
    }

    public static  String randomCode() {
        int code = random.nextInt(999999);
        StringBuilder sb = new StringBuilder();
        sb.append(code);
        while (sb.length() < 6) {
            sb.insert(0, 0);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(md5("18516908835"));
        System.out.println(uuid());
        System.out.println(SmsHelper.send("18516908835", randomCode()));
    }
}
