package com.king.frame.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

/**
 *  App/系统相关工具类
 *
 * @author king
 * @date 2019-11-27 10:33
 */
object AppUtil {

    /**
     * 获取手机安卓版本代号
     * @return
     */
    val androidVersion: Int
        get() = Build.VERSION.SDK_INT

    /**
     * 获取手机机型
     * @return
     */
    val mobileModel: String
        get() = android.os.Build.MODEL

    /**
     * 获取手机生产厂家
     * @return
     */
    val mobileManufacturer: String
        get() = android.os.Build.MANUFACTURER

    /**
     * 获取app版本
     * @param context
     * @return
     */
    fun getAppVersionCode(context: Context?): Int {
        if (context != null) {
            val pm = context.packageManager
            if (pm != null) {
                val pi: PackageInfo?
                try {
                    pi = pm.getPackageInfo(context.packageName, 0)
                    if (pi != null) {
                        return pi.versionCode
                    }
                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                }

            }
        }
        return -1
    }

    /**
     * 获取app版本名
     * @param context
     * @return
     */
    fun getAppVersionName(context: Context?): String? {
        if (context != null) {
            val pm = context.packageManager
            if (pm != null) {
                val pi: PackageInfo?
                try {
                    pi = pm.getPackageInfo(context.packageName, 0)
                    if (pi != null) {
                        return pi.versionName
                    }
                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                }

            }
        }
        return null
    }
}
