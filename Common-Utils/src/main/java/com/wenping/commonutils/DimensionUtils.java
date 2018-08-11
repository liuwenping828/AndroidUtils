package com.wenping.commonutils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 尺寸转换工具类 (sp dp px)
 */
public class DimensionUtils {

    private DimensionUtils() {}

    public static float dp2px(@NonNull final Context context, final float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    public static float sp2px(@NonNull final Context context, final float sp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, displayMetrics);
    }

    public static float px2dp(@NonNull final Context context, final float px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return px / ((float) displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float px2sp(@NonNull final Context context, final float px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return px / displayMetrics.scaledDensity;
    }
}
