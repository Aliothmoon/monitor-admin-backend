package com.swust.aliothmoon.context;

import lombok.experimental.UtilityClass;


@UtilityClass
public class TokenHeaderContext {
    private static final ThreadLocal<String> LOCAL = new ThreadLocal<>();

    public static void set(String val) {
        LOCAL.set(val);
    }

    public static String get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }

}
