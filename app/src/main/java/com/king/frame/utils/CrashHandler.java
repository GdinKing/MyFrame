package com.king.frame.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.king.frame.BaseApplication;

/**
 * 崩溃日志处理类
 *
 * @author king
 * @date 2017/3/6 16:03
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Context mContext;
    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private static CrashHandler mInstance;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (mInstance == null) {
            synchronized (CrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new CrashHandler();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(t, ex);
        } else {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LogUtil.e(e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    public boolean handleException(Throwable ex) {

        if (ex == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "抱歉,程序出现异常", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        //保存错误日志
        LogUtil.error2File(getCrashLog(ex));
        return true;
    }

    /**
     * 获取指定格式的崩溃日志
     * @param e
     * @return
     */
    private String getCrashLog(Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------------------------------" + "\n");
        sb.append("Version: " + AppUtil.getAppVersionCode(BaseApplication.getInstance()) + "\n");
        sb.append("VersionName: " + AppUtil.getAppVersionName(BaseApplication.getInstance()) + "\n");
        sb.append("Android: " + android.os.Build.VERSION.RELEASE + "\n"); //固件版本
        sb.append("Manufacturer: " + android.os.Build.MANUFACTURER + "\n");//生产厂家
        sb.append("Model: " + android.os.Build.MODEL + "\n"); //机型
        sb.append("Date: " + TimeUtil.getCurrentTime(TimeUtil.SECOND_STR) + "\n");
        sb.append("ErrorMsg: " + LogUtil.getErrorMsg(e) + "\n");
        sb.append("\n");
        return sb.toString();
    }
}
