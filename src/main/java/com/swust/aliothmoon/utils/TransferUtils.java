package com.swust.aliothmoon.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.ConvertException;
import com.esotericsoftware.reflectasm.ConstructorAccess;
import lombok.experimental.UtilityClass;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class TransferUtils {
    private static final ConcurrentHashMap<Class<?>, ConstructorAccess<?>> CONSTRUCTOR_CACHE = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Pair<Class<?>, Class<?>>, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();
    private static final ReflectConverter C = new ReflectConverter();

    @SuppressWarnings("unchecked")
    public static <T> T to(Object from, Class<?> to) {
        if (from == null) {
            return null;
        }
        ConstructorAccess<?> access = CONSTRUCTOR_CACHE.computeIfAbsent(to, ConstructorAccess::get);
        Object target = access.newInstance();
        Pair<Class<?>, Class<?>> key = Pair.of(from.getClass(), to);
        BeanCopier copier = BEAN_COPIER_CACHE.computeIfAbsent(key, p ->
                BeanCopier.create(p.getFirst(), p.getSecond(), true)
        );
        copier.copy(from, target, C);
        return (T) target;
    }

    /**
     * 将对象列表转换为指定类型的列表
     *
     * @param fromList 源列表
     * @param to       目标类型
     * @param <T>      目标类型参数
     * @return 转换后的列表
     */
    public static <T> List<T> toList(List<?> fromList, Class<T> to) {
        if (fromList == null || fromList.isEmpty()) {
            return new ArrayList<>();
        }

        List<T> result = new ArrayList<>(fromList.size());
        for (Object from : fromList) {
            result.add(to(from, to));
        }

        return result;
    }

    /**
     * 复制属性
     *
     * @param from   源对象
     * @param to     目标对象
     */
    public static void copyProperties(Object from, Object to) {
        if (from == null || to == null) {
            return;
        }

        Pair<Class<?>, Class<?>> key = Pair.of(from.getClass(), to.getClass());
        BeanCopier copier = BEAN_COPIER_CACHE.computeIfAbsent(key, p -> BeanCopier.create(p.getFirst(), p.getSecond(), false));
        copier.copy(from, to, null);
    }

    private static class ReflectConverter implements Converter {

        @Override
        public Object convert(Object value, Class clazz, Object context) {
            try {
                return Convert.convert(clazz, value);
            } catch (ConvertException e) {
                if (clazz.isPrimitive()) {
                    if (clazz == int.class) {
                        return 0;
                    } else if (clazz == boolean.class) {
                        return false;
                    } else if (clazz == char.class) {
                        return '\u0000';
                    } else if (clazz == byte.class) {
                        return (byte) 0;
                    } else if (clazz == short.class) {
                        return (short) 0;
                    } else if (clazz == long.class) {
                        return 0L;
                    } else if (clazz == float.class) {
                        return 0.0f;
                    } else if (clazz == double.class) {
                        return 0.0d;
                    }
                }
                return ThrowsUtils.doThrow(e);
            }
        }
    }
}
