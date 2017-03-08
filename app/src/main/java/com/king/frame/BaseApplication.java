package com.king.frame;

import android.app.Application;

import com.king.frame.utils.CrashHandler;

/**
 * @author king
 * @date 2017/3/1 16:37
 */

public class BaseApplication extends Application {

    private static BaseApplication mInstance;

    public static BaseApplication getInstance() {

        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //注册崩溃处理
        Thread.UncaughtExceptionHandler currentHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (!(currentHandler instanceof CrashHandler)) {
            CrashHandler exceptionHandler = CrashHandler.getInstance();
            exceptionHandler.init(this);
        }
        //在这里初始化一些其他东西，例如百度地图的初始化，友盟的初始化

    }
}
