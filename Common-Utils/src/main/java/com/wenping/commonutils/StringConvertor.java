package com.wenping.commonutils;

import android.support.annotation.NonNull;

/**
 * 字符串转换工具类
 */
public class StringConvertor {

    private StringConvertor() {
    }
    
    public static String capitalize(@NonNull String str) {
        char[] chars = str.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') {
                found = false;
            }
        }
        return String.valueOf(chars);
    }
}
