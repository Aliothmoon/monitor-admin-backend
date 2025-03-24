package com.swust.aliothmoon.utils;

import cn.hutool.extra.spring.SpringUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

@Slf4j
@UtilityClass
public class SpringUtils extends SpringUtil {

    public static final String SEPARATOR = ",";

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     */
    public static boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

    /**
     * @return Class 注册对象的类型
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return getBeanFactory().getType(name);
    }

    /**
     * 获取aop代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker) {
        try {
            return (T)AopContext.currentProxy();
        } catch (IllegalStateException e) {
            log.warn("fallback aop proxy", e.getCause());
            ThrowsUtils.throwIfNull(invoker, () -> new IllegalArgumentException("invoker is null"));
            return context().getBean((Class<T>)invoker.getClass());
        }
    }

    /**
     * 获取spring上下文
     */
    public static ApplicationContext context() {
        return getApplicationContext();
    }
}
