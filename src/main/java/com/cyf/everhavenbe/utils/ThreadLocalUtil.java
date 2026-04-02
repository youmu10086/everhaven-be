package com.cyf.everhavenbe.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocal 工具类，用于存储当前线程的用户信息（如 JWT claims）
 */
@SuppressWarnings("all")
public class ThreadLocalUtil {
    // 提供 ThreadLocal 对象
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    // 根据键获取值
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    // 存储键值对
    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }

    // 清除 ThreadLocal，防止内存泄漏
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
