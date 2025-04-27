package com.swust.aliothmoon.utils;

import cn.hutool.core.util.StrUtil;
import com.esotericsoftware.reflectasm.MethodAccess;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class BeanUtil {

    public static final String SERIALIZABLE_LAMBDA_IMPL_METHOD = "writeReplace";
    private static final ConcurrentHashMap<Class<?>, MethodAccess> cache = new ConcurrentHashMap<>();

    public static MethodAccess getMethodAccess(Object o) {
        Class<?> clz = o.getClass();
        return cache.computeIfAbsent(clz, MethodAccess::get);
    }

    public static String getSetterByPropertyName(String propertyName) {
        return StrUtil.upperFirstAndAddPre(propertyName, "set");
    }

    @SneakyThrows
    public String getLambdaImplMethodFullName(Serializable serializable) {
        Method method = serializable.getClass().getDeclaredMethod(SERIALIZABLE_LAMBDA_IMPL_METHOD);
        method.setAccessible(true);
        SerializedLambda lambda = (SerializedLambda) method.invoke(serializable);
        return (lambda.getImplClass() + "." + lambda.getImplMethodName()).replace('/', '.');
    }
}
