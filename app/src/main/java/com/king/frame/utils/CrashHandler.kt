package com.king.frame.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

import com.king.frame.BaseApplication
import kotlinx.coroutines.*
import kotlinx.coroutines.android.HandlerDispatcher

/**
 * 崩溃日志处理类
 *
 * @author king
 * @date 2017/3/6 16:03
 */

class CrashHandler private constructor() : Thread.UncaughtExceptionHandler {
    //单例模式
    private object SingleInstance {
        val INSTANCE = CrashHandler();
    }
    companion object {
        val instance: CrashHandler
            get() = SingleInstance.INSTANCE
    }

    private var mContext: Context? = null
    // 系统默认的UncaughtException处理类
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null

    fun init(context: Context) {
        this.mContext = context
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler!!.uncaughtException(t, ex)
        } else {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                LogUtil.e(e)
            }

            // 退出程序
            System.exit(0)
        }
    }

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob) //指定主作用域

    fun handleException(ex: Throwable?): Boolean {

        if (ex == null) {
            return false
        }
        Thread{
            run {
                LogUtil.error2File(getCrashLog(ex))
                Looper.prepare()
                Toast.makeText(mContext, "抱歉,程序出现异常", Toast.LENGTH_LONG).show()
                Looper.loop()
            }
        }.start()

        return true
    }

    /**
     * 获取指定格式的崩溃日志
     * @param e
     * @return
     */
    private fun getCrashLog(e: Throwable?): String {
        val sb = StringBuilder()
        sb.append("------------------------------------------------------" + "\n")
        sb.append("Version: " + AppUtil.getAppVersionCode(BaseApplication.instance) + "\n")
        sb.append("VersionName: " + AppUtil.getAppVersionName(BaseApplication.instance) + "\n")
        sb.append("Android: " + android.os.Build.VERSION.RELEASE + "\n") //固件版本
        sb.append("Manufacturer: " + android.os.Build.MANUFACTURER + "\n")//生产厂家
        sb.append("Model: " + android.os.Build.MODEL + "\n") //机型
        sb.append("Date: " + TimeUtil.getCurrentTime(TimeUtil.SECOND_STR) + "\n")
        sb.append("ErrorMsg: " + LogUtil.getErrorMsg(e) + "\n")
        sb.append("\n")
        return sb.toString()
    }

}
