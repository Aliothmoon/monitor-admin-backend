package com.swust.aliothmoon.utils;

import com.alibaba.fastjson2.JSON;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.util.Lazy;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisUtils {
    private static final Lazy<StringRedisTemplate> template = Lazy.of(() -> SpringUtils.getBean(StringRedisTemplate.class));


    private static StringRedisTemplate get() {
        return template.get();
    }

    /**
     * 存储字符串值
     * @param key 键
     * @param value 值
     */
    public static void set(String key, String value) {
        get().opsForValue().set(key, value);
    }

    public static void set(String key, Object value) {
        String str = JSON.toJSONString(value);
        set(key, str);
    }

    /**
     * 存储字符串值并设置过期时间
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public static void set(String key, String value, long timeout, TimeUnit unit) {
        get().opsForValue().set(key, value, timeout, unit);
    }

    public static void set(String key, Object value, long timeout, TimeUnit unit) {
        String str = JSON.toJSONString(value);
        set(key, str, timeout, unit);
    }

    /**
     * 获取字符串值
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        return get().opsForValue().get(key);
    }

    public static <T> T getVal(String key, Class<T> clz) {
        String val = get().opsForValue().get(key);
        if (val == null) {
            return null;
        }
        return JSON.parseObject(val, clz);
    }

    /**
     * 删除键
     * @param key 键
     * @return 是否成功
     */
    public static Boolean delete(String key) {
        return get().delete(key);
    }

    /**
     * 批量删除键
     * @param keys 键集合
     * @return 删除的键数量
     */
    public static Long delete(Collection<String> keys) {
        return get().delete(keys);
    }

    /**
     * 设置过期时间
     * @param key 键
     * @param timeout 过期时间
     * @param unit 时间单位
     * @return 是否成功
     */
    public static Boolean expire(String key, long timeout, TimeUnit unit) {
        return get().expire(key, timeout, unit);
    }

    /**
     * 判断键是否存在
     * @param key 键
     * @return 是否存在
     */
    public static Boolean hasKey(String key) {
        return get().hasKey(key);
    }

    /**
     * 递增
     * @param key 键
     * @param delta 增量
     * @return 增加后的值
     */
    public static Long increment(String key, long delta) {
        return get().opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * @param key 键
     * @param delta 减量
     * @return 减少后的值
     */
    public static Long decrement(String key, long delta) {
        return get().opsForValue().decrement(key, delta);
    }

    // Hash 操作

    /**
     * 设置Hash中的字段值
     * @param key 键
     * @param field 字段
     * @param value 值
     */
    public static void hSet(String key, String field, String value) {
        get().opsForHash().put(key, field, value);
    }

    /**
     * 批量设置Hash字段值
     * @param key 键
     * @param map 字段值映射
     */
    public static void hSetAll(String key, Map<String, String> map) {
        get().opsForHash().putAll(key, map);
    }

    /**
     * 获取Hash中的字段值
     * @param key 键
     * @param field 字段
     * @return 值
     */
    public static Object hGet(String key, String field) {
        return get().opsForHash().get(key, field);
    }

    /**
     * 获取Hash中的所有字段值
     * @param key 键
     * @return 字段值映射
     */
    public static Map<Object, Object> hGetAll(String key) {
        return get().opsForHash().entries(key);
    }

    /**
     * 删除Hash中的字段
     * @param key 键
     * @param fields 字段
     * @return 删除的字段数
     */
    public static Long hDelete(String key, Object... fields) {
        return get().opsForHash().delete(key, fields);
    }

    /**
     * 判断Hash中是否存在字段
     * @param key 键
     * @param field 字段
     * @return 是否存在
     */
    public static Boolean hHasKey(String key, String field) {
        return get().opsForHash().hasKey(key, field);
    }

    // List 操作

    /**
     * 向List左端添加元素
     * @param key 键
     * @param value 值
     * @return List长度
     */
    public static Long lLeftPush(String key, String value) {
        return get().opsForList().leftPush(key, value);
    }

    /**
     * 向List右端添加元素
     * @param key 键
     * @param value 值
     * @return List长度
     */
    public static Long lRightPush(String key, String value) {
        return get().opsForList().rightPush(key, value);
    }

    /**
     * 从List左端弹出元素
     * @param key 键
     * @return 弹出的元素
     */
    public static String lLeftPop(String key) {
        return get().opsForList().leftPop(key);
    }

    /**
     * 从List右端弹出元素
     * @param key 键
     * @return 弹出的元素
     */
    public static String lRightPop(String key) {
        return get().opsForList().rightPop(key);
    }

    /**
     * 获取List范围内的元素
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置
     * @return 元素列表
     */
    public static List<String> lRange(String key, long start, long end) {
        return get().opsForList().range(key, start, end);
    }

    /**
     * 获取List长度
     * @param key 键
     * @return 长度
     */
    public static Long lSize(String key) {
        return get().opsForList().size(key);
    }

    // Set 操作

    /**
     * 向Set添加元素
     * @param key 键
     * @param values 值
     * @return 添加的元素数
     */
    public static Long sAdd(String key, String... values) {
        return get().opsForSet().add(key, values);
    }

    /**
     * 从Set移除元素
     * @param key 键
     * @param values 值
     * @return 移除的元素数
     */
    public static Long sRemove(String key, Object... values) {
        return get().opsForSet().remove(key, values);
    }

    /**
     * 获取Set所有元素
     * @param key 键
     * @return 元素集合
     */
    public static Set<String> sMembers(String key) {
        return get().opsForSet().members(key);
    }

    /**
     * 判断Set是否包含元素
     * @param key 键
     * @param value 值
     * @return 是否包含
     */
    public static Boolean sIsMember(String key, Object value) {
        return get().opsForSet().isMember(key, value);
    }

    // ZSet 操作

    /**
     * 向ZSet添加元素
     * @param key 键
     * @param value 值
     * @param score 分数
     * @return 是否成功
     */
    public static Boolean zAdd(String key, String value, double score) {
        return get().opsForZSet().add(key, value, score);
    }

    /**
     * 从ZSet移除元素
     * @param key 键
     * @param values 值
     * @return 移除的元素数
     */
    public static Long zRemove(String key, Object... values) {
        return get().opsForZSet().remove(key, values);
    }

    /**
     * 获取ZSet范围内的元素
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置
     * @return 元素集合
     */
    public static Set<String> zRange(String key, long start, long end) {
        return get().opsForZSet().range(key, start, end);
    }

    /**
     * 获取ZSet中元素的分数
     * @param key 键
     * @param value 值
     * @return 分数
     */
    public static Double zScore(String key, Object value) {
        return get().opsForZSet().score(key, value);
    }
}
