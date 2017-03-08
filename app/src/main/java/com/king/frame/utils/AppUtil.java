package com.king.frame.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * @author king
 * @date 2017/3/6 16:33
 */

public class AppUtil {

    /**
     * 获取app版本
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            if (pm != null) {
                PackageInfo pi;
                try {
                    pi = pm.getPackageInfo(context.getPackageName(), 0);
                    if (pi != null) {
                        return pi.versionCode;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    /**
     * 获取app版本名
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            if (pm != null) {
                PackageInfo pi;
                try {
                    pi = pm.getPackageInfo(context.getPackageName(), 0);
                    if (pi != null) {
                        return pi.versionName;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取手机安卓版本代号
     * @return
     */
    public static int getAndroidVersion(){
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机机型
     * @return
     */
    public static String getMobileModel(){
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机生产厂家
     * @return
     */
    public static String getMobileManufacturer(){
        return android.os.Build.MANUFACTURER;
    }
}
