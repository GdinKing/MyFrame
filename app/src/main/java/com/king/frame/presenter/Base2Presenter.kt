package com.king.frame.presenter

import androidx.lifecycle.*

/**
 * 基础的presenter，封装一些通用操作,关联Activity生命周期等
 *
 * DefaultLifecycleObserver内部已经定义了onStart()等方法
 * @author king
 * @date 2019-11-27 11:24
 */
interface Base2Presenter : DefaultLifecycleObserver {

}
