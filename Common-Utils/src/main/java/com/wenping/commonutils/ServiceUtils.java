package com.wenping.commonutils;

import android.app.ActivityManager;
import android.app.ActivityManager.*;
import android.content.Context;
import android.support.annotation.NonNull;

public class ServiceUtils {

    private ServiceUtils() {
    }

    /**
     * 判断服务是否运行
     *
     * @param context
     * @param cls
     * @return
     */
    public static boolean isRunning(@NonNull Context context, @NonNull Class<?> cls) {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
