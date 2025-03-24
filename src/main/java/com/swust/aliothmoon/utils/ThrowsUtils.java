package com.swust.aliothmoon.utils;

import com.swust.aliothmoon.exception.ServiceException;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.function.Supplier;

@UtilityClass
public class ThrowsUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param e         异常
     */
    public static void throwIf(boolean condition, RuntimeException e) {
        if (condition) {
            throw e;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param obj obj
     * @param e   异常
     */
    public static void throwIfNull(Object obj, String e) {
        throwIf(obj == null, new ServiceException(e));
    }

    /**
     * 如果为 null，则为 throw
     *
     * @param obj obj
     * @param e   e
     */
    public static void throwIfNull(Object obj, RuntimeException e) {
        throwIf(obj == null, e);
    }

    public static void throwIfNull(Object obj, Supplier<? extends Exception> e) {
        throwIf(obj == null, e);
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param e         异常
     */
    public static void throwIf(boolean condition, String e) {
        throwIf(condition, new ServiceException(e));
    }

    public static void throwIf(Supplier<Boolean> supplier, String e) {
        throwIf(Boolean.TRUE.equals(supplier.get()), e);
    }


    public static void throwIfEqual(Object a, Object b, String e) {
        throwIf(Objects.equals(a, b), new ServiceException(e));
    }

    public static void throwIf(boolean condition, Supplier<? extends Exception> e) {
        if (condition) {
            doThrow(e.get());
        }
    }

    public static <T> T assertIfNull(T obj, String msg) {
        throwIfNull(obj, msg);
        return obj;
    }

    public static <T> T throwException(String e) {
        throw new ServiceException(e);
    }

    public static <T> Supplier<T> ex(String e) {
        return () -> throwException(e);
    }


    @SuppressWarnings("unchecked")
    public static <E extends Exception, R> R doThrow(Exception e) throws E {
        throw (E) e;
    }


}
