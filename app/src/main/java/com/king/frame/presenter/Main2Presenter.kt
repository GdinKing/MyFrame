package com.king.frame.presenter

import androidx.lifecycle.*
import com.king.frame.config.AppConfig
import com.king.frame.model.HttpHelper
import com.king.frame.utils.LogUtil
import com.king.frame.viewmodel.MainViewBinder
import kotlinx.coroutines.*
import java.io.IOException

/**
 * MainActivity的Presenter  写法2
 *
 * DefaultLifecycleObserver
 *
 * @author king
 * @date 2019-11-27 11:27
 */
class Main2Presenter(private val mViewBinder: MainViewBinder?) : Base2Presenter {

    override fun onCreate(owner: LifecycleOwner) {
    }

    override fun onStart(owner: LifecycleOwner) {
    }

    override fun onStop(owner: LifecycleOwner) {
    }

    override fun onDestroy(owner: LifecycleOwner) {
    }

}
