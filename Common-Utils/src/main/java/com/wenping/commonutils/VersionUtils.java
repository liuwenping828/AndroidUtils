package com.wenping.commonutils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.*;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 版本工具类
 */
public class VersionUtils {

    private VersionUtils() {
    }

    /**
     * 获取版本名
     *
     * @param context
     * @return String
     */
    public static String getVersionName(@NonNull Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return int
     */
    public static int getVersionCode(@NonNull Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            return -1;
        }
    }

    /**
     * 是否支持 OpenGlEs2
     *
     * @param context
     * @return
     */
    public static boolean isSupportedOpenGlEs2(@NonNull Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        return configurationInfo.reqGlEsVersion >= 0x20000;
    }

    /**
     * 比较两个版本名
     * Compares two version strings, using Semantic Versioning convention.
     *
     * @param v1 first version string.
     * @param v2 second version string.
     * @return 0 if the versions are equal, 1 if version v1 is before version v2,
     * -1 if version v1 is after version v2, -2 if version format is invalid.
     */
    public static int compareVersions(@Nullable String v1, @Nullable String v2) {
        if (v1 == null || v2 == null || v1.trim().equals("") || v2.trim().equals("")) return -2;
        else if (v1.equals(v2)) return 0;
        else {
            boolean valid1 = v1.matches("\\d+\\.\\d+\\.\\d+");
            boolean valid2 = v2.matches("\\d+\\.\\d+\\.\\d+");

            if (valid1 && valid2) {
                int[] nums1;
                int[] nums2;

                try {
                    nums1 = convertStringArrayToIntArray(v1.split("\\."));
                    nums2 = convertStringArrayToIntArray(v2.split("\\."));
                } catch (NumberFormatException e) {
                    return -2;
                }

                if (nums1.length == 3 && nums2.length == 3) {
                    if (nums1[0] < nums2[0]) return 1;
                    else if (nums1[0] > nums2[0]) return -1;
                    else {
                        if (nums1[1] < nums2[1]) return 1;
                        else if (nums1[1] > nums2[1]) return -1;
                        else {
                            if (nums1[2] < nums2[2]) return 1;
                            else if (nums1[2] > nums2[2]) return -1;
                            else {
                                return 0;
                            }
                        }
                    }
                } else {
                    return -2;
                }
            } else {
                return -2;
            }
        }
    }

    private static int[] convertStringArrayToIntArray(String[] stringArray) throws NumberFormatException {
        if (stringArray != null) {
            int intArray[] = new int[stringArray.length];
            for (int i = 0; i < stringArray.length; i++) {
                intArray[i] = Integer.parseInt(stringArray[i]);
            }
            return intArray;
        }
        return null;
    }

}
