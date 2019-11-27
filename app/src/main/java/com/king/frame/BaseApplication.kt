package com.king.frame

import android.app.Application

import com.king.frame.utils.CrashHandler

/**
 * application
 *
 * @author king
 * @date 2017/3/1 16:37
 */
class BaseApplication : Application() {

    companion object {
        var instance: BaseApplication? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //注册崩溃处理
        val currentHandler = Thread.getDefaultUncaughtExceptionHandler()
        if (currentHandler !is CrashHandler) {
            CrashHandler.instance.init(this)
        }
        //在这里初始化一些其他东西，例如百度地图的初始化

    }

}
