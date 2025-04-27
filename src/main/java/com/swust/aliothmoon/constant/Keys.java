package com.swust.aliothmoon.constant;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Keys {
    public static final UnaryOperator<String> TOKEN = key -> "token:" + key;
}
