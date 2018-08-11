package com.wenping.commonutils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 验证邮箱日期工具类
 */
public class ValidationUtils {

    private ValidationUtils() {
    }

    /**
     * 判断是否是邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmailValid(@Nullable CharSequence email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * 验证日期
     *
     * @param date
     * @param format
     * @return
     */
    public static boolean isDateValid(@Nullable String date, @NonNull String format) {
        if (date == null) {
            return false;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        simpleDateFormat.setLenient(false);

        try {
            simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
