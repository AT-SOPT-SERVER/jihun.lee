package org.sopt.global.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringUtils {
    public static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }
}
