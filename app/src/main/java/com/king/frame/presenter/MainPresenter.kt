package com.king.frame.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.king.frame.config.AppConfig
import com.king.frame.model.HttpHelper
import com.king.frame.utils.LogUtil
import com.king.frame.viewmodel.MainViewBinder
import kotlinx.coroutines.*
import java.io.IOException

/**
 * MainActivity的Presenter
 *
 * 引入Lifecycle，使presenter可以关联Activity/fragment组件生命周期
 * @author king
 * @date 2019-11-27 11:27
 */
class MainPresenter(private val mViewBinder: MainViewBinder?) : BasePresenter{

    //这两个可以声明在IBasePresenter，这里为了区分两种写法，直接在这里声明了
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob) //指定主作用域

    override fun onCreate(owner: LifecycleOwner) {
        LogUtil.i("onCreate()")
    }

    override fun onStart(owner: LifecycleOwner) {
        LogUtil.i("onStart()")
    }

    override fun onStop(owner: LifecycleOwner) {
        LogUtil.i("onStop()")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        LogUtil.i("onDestroy()")
        if(owner.lifecycle?.currentState!!.isAtLeast(Lifecycle.State.DESTROYED)){
            LogUtil.i("销毁了")
            //取消协程
            viewModelJob.cancel()
        }
    }

    /**
     * 获取数据
     *
     * @param name
     * @param tag
     * @param page
     * @param count
     */
    fun getMenuJson(name: String, page: Int, count: Int) {

        mViewBinder?.showProgress()

        uiScope.launch {//主线程（UI线程）
            var result = ""
            try{
                val deferred = async(Dispatchers.IO){//切换至异步线程执行
                   val param = mapOf("key" to AppConfig.JUHE_KEY, "menu" to name,"pn" to page.toString(),"rn" to count.toString())
                   HttpHelper.instance.get(AppConfig.FOOD_MENU_URL, param)
                }
                result = deferred.await()
                mViewBinder?.hideProgress()
                mViewBinder?.showBookData(result,null)
            }catch (e:IOException){
                mViewBinder?.hideProgress()
                mViewBinder?.showBookData("", e)
            }
        }
    }
}
