package org.sopt.global.utils;

public class StringUtils {
    private StringUtils() {}

    public static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }
}
