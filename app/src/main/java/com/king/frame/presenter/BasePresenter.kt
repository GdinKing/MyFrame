package com.king.frame.presenter

import android.provider.Contacts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

/**
 * 基础的presenter，封装一些通用操作
 *
 * @author king
 * @date 2019-11-27 11:24
 */
abstract class BasePresenter {
    //声明通用的作用域，可以在子Presenter直接使用
//    protected val viewModelJob = SupervisorJob()
//    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob) //指定主作用域
    abstract fun onCreate()

    abstract fun onStart()

    abstract fun onStop()

    abstract fun onDestory()
}
