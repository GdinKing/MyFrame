package com.king.frame.presenter

import com.king.frame.config.AppConfig
import com.king.frame.model.HttpHelper
import com.king.frame.view.MainViewBinder
import kotlinx.coroutines.*
import java.io.IOException

/**
 * MainActivity的Presenter写法2
 * 使用了MainScope的协程作用域
 *
 * @author king
 * @date 2019-11-27 11:27
 */
class Main2Presenter(private val mViewBinder: MainViewBinder?) : BasePresenter(),CoroutineScope by MainScope() {

    override fun onCreate() {
    }

    override fun onStart() {}

    override fun onStop() {}

    override fun onDestory() {
        //取消协程，防止
        cancel()
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

        launch {//主线程（UI线程）
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
