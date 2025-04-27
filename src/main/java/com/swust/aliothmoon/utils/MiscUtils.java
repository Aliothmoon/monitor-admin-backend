package com.swust.aliothmoon.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.function.Supplier;

@UtilityClass
public class MiscUtils {

    public static <T> T ifNull(final T object, final T defaultValue) {
        return object != null ? object : defaultValue;
    }

    public static <T> T ifNull(final T object, final Supplier<T> defaultValue) {
        return object != null ? object : defaultValue.get();
    }

    public static <T> boolean eq(final T a, final T b) {
        return Objects.equals(a, b);
    }

    public static <T> int eqi(final T a, final T b) {
        return Objects.equals(a, b) ? 1 : 0;
    }

    @SafeVarargs
    public static <T> boolean in(final T key, final T... values) {
        for (T val : values) {
            if (eq(key, val))
                return true;
        }
        return false;
    }

    @SafeVarargs
    public static <T> boolean notIn(final T key, final T... values) {
        return !in(key, values);
    }
}
