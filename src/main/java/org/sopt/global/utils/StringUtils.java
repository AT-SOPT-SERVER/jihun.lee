package org.sopt.global.utils;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringUtils {
    public static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

    public static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
}
