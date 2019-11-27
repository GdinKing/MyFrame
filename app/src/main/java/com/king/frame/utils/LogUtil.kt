package com.king.frame.utils

import android.text.TextUtils
import android.util.Log
import com.king.frame.BuildConfig

import com.king.frame.config.AppConfig

import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

/**
 * 日志工具类
 *
 * @author king
 * @date 2017/3/6 14:40
 */

object LogUtil {

    /**
     * 调试模式开启Log，发布版本关闭
     */
    var isDebug = BuildConfig.DEBUG

    val TAG = "king"

    fun i(tr: Throwable) {
        if (isDebug) {
            Log.i(TAG, tr.message, tr)
        }
    }

    fun e(tr: Throwable) {
        if (isDebug) {
            Log.e(TAG, tr.message, tr)
        }
    }

    fun e(msg: String) {
        if (isDebug) {
            Log.e(TAG, msg)
        }
    }

    fun i(msg: String) {
        if (isDebug) {
            Log.i(TAG, msg)
        }
    }

    /**
     * 错误日志写入文件
     *
     * @param tr
     */
    fun error2File(tr: Throwable) {
        if (isDebug) {
            Log.e(TAG, tr.message, tr)
        }
        writeLogFile(getErrorMsg(tr))
    }

    /**
     * 错误日志写入文件
     *
     * @param error
     */
    fun error2File(error: String) {
        if (isDebug) {
            Log.e(TAG, error)
        }
        writeLogFile(error)
    }


    /**
     * 获取错误信息
     *
     * @param tr
     * @return
     */
    fun getErrorMsg(tr: Throwable?): String {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr?.printStackTrace(pw)
        return tr.toString()
    }

    /**
     * 写日志文件
     *
     * @param error
     */
    private fun writeLogFile(error: String) {
        var error = error
        try {
            val filePath = AppConfig.LOG_FILE_PATH + "error.log"
            val file = File(filePath)
            if (!file.exists()) {
                file.parentFile.mkdirs()
                file.createNewFile()
            }

            if (!TextUtils.isEmpty(error)) {
                val currentTime = TimeUtil.getCurrentTime(TimeUtil.SECOND_STR)
                error = currentTime + "\n" + error + "\n"
                FileUtil.appendFileContent(filePath, error)
            }
        } catch (e: Exception) {

        }

    }
}
