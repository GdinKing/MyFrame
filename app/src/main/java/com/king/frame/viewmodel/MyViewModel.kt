package com.king.frame.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.king.frame.base.BaseViewModel
import com.king.frame.utils.TimeUtil
import java.util.*

/**
 * “我的”ViewModel
 *
 * @author king
 * @date 2019-11-29 10:39
 */
class MyViewModel(private val tips:String?) :BaseViewModel() {

    val timeData = MutableLiveData<String>()

    init {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val dataStr = TimeUtil.dateToSecondStr(Date())
                //异步线程使用postValue()，主线程用setValue()/postValue()
                timeData.postValue(tips+dataStr)
            }
        },0,1000)
    }

    //构造函数带参数，需要构建一个Factory，这样就可以用
    //ViewModelProviders.of(this, MyViewModel.Factory(tips)).get(MyViewModel::class.java)执行带参数的构造函数
    class Factory(private val tips:String?) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MyViewModel(tips) as T
        }
    }
}