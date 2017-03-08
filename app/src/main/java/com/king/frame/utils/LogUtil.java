package com.king.frame.utils;

import android.text.TextUtils;
import android.util.Log;

import com.king.frame.config.AppConfig;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 日志工具类
 *
 * @author king
 * @date 2017/3/6 14:40
 */

public class LogUtil {

    /**
     * 调试模式开启Log，发布版本关闭
     */
    public static boolean isDebug = true;

    public static final String TAG = "king";

    public static void i(Throwable tr) {
        if (isDebug) {
            Log.i(TAG, tr.getMessage(), tr);
        }
    }

    public static void e(Throwable tr) {
        if (isDebug) {
            Log.e(TAG, tr.getMessage(), tr);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (isDebug) {
            Log.i(TAG, msg);
        }
    }

    /**
     * 错误日志写入文件
     *
     * @param tr
     */
    public static void error2File(Throwable tr) {
        if (isDebug) {
            Log.e(TAG, tr.getMessage(), tr);
        }
        writeLogFile(getErrorMsg(tr));
    }

    /**
     * 错误日志写入文件
     *
     * @param error
     */
    public static void error2File(String error) {
        if (isDebug) {
            Log.e(TAG, error);
        }
        writeLogFile(error);
    }


    /**
     * 获取错误信息
     *
     * @param tr
     * @return
     */
    public static String getErrorMsg(Throwable tr) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        return tr.toString();
    }

    /**
     * 写日志文件
     *
     * @param error
     */
    private static void writeLogFile(String error) {
        try {
            String filePath = AppConfig.LOG_FILE_PATH + "error.log";
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            if (!TextUtils.isEmpty(error)) {
                String currentTime = TimeUtil.getCurrentTime(TimeUtil.SECOND_STR);
                error = currentTime + "\n" + error + "\n";
                FileUtil.appendFileContent(filePath, error);
            }
        } catch (Exception e) {

        }
    }
}
