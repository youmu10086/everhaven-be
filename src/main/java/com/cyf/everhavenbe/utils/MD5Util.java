package com.cyf.everhavenbe.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 工具类
 */
public class MD5Util {

    /**
     * 获取字符串的 MD5 值（32位小写）
     *
     * @param input 输入字符串
     * @return MD5 哈希值，若输入为 null 则返回 null
     */
    public static String getMD5String(String input) {
        if (input == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // MD5 是标准算法，理论上不会抛此异常
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    /**
     * 获取字符串的 MD5 值（16位小写，取32位的中间部分）
     *
     * @param input 输入字符串
     * @return 16位 MD5 哈希值
     */
    public static String getMD5String16(String input) {
        String md5 = getMD5String(input);
        if (md5 == null || md5.length() != 32) {
            return null;
        }
        return md5.substring(8, 24);
    }

    /**
     * 验证字符串与 MD5 是否匹配
     *
     * @param input   原始字符串
     * @param md5Hash 预期的 MD5 值
     * @return 是否匹配
     */
    public static boolean verify(String input, String md5Hash) {
        String computed = getMD5String(input);
        return computed != null && computed.equalsIgnoreCase(md5Hash);
    }

    // 简单测试
    public static void main(String[] args) {
        String raw = "123456";
        String md5 = getMD5String(raw);
        System.out.println("MD5(32位): " + md5);
        System.out.println("MD5(16位): " + getMD5String16(raw));
        System.out.println("验证: " + verify(raw, md5));
    }
}
