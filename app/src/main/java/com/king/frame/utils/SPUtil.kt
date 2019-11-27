package com.king.frame.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * SharedPreferences工具类
 *
 * @author king
 * @date 2019-11-27 14:19
 */

object SPUtil {

    val SP_NAME = "frame_config"

    /**
     * 设置String值
     *
     * @param context
     * @param key
     * @param value
     */
    fun putString(context: Context, key: String, value: String) {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * 设置Int值
     *
     * @param context
     * @param key
     * @param value
     */
    fun putInt(context: Context, key: String, value: Int) {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    /**
     * 设置boolean值
     *
     * @param context
     * @param key
     * @param value
     */
    fun putBoolean(context: Context, key: String, value: Boolean) {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    /**
     * 获取boolean值
     *
     * @param context
     * @param key
     * @return
     */
    fun getBoolean(context: Context, key: String): Boolean {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(key, false)
    }

    /**
     * 获取String值
     *
     * @param context
     * @param key
     * @return
     */
    fun getString(context: Context, key: String): String? {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getString(key, "")
    }

    /**
     * 获取int值
     *
     * @param context
     * @param key
     * @return
     */
    fun getInt(context: Context, key: String): Int {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getInt(key, -1)
    }
}
