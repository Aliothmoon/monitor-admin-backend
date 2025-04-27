package com.swust.aliothmoon.constant

object Keys {
    @JvmField
    val TOKEN: (String) -> String = {
        "token:$it"
    }
}