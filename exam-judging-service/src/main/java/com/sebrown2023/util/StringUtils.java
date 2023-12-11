package com.sebrown2023.util;

import java.util.List;

public final class StringUtils {
    private StringUtils() {
    }

    public static <T> String joinToString(List<T> list, String separator) {
        var sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        for (T value : list) {
            if (first) {
                first = false;
            } else {
                sb.append(separator);
            }
            sb.append(value.toString());
        }
        sb.append("]");
        return sb.toString();
    }
}
