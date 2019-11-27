package com.king.frame.presenter

import com.king.frame.config.AppConfig
import com.king.frame.model.HttpHelper
import com.king.frame.view.MainViewBinder
import kotlinx.coroutines.*
import java.io.IOException

/**
 * MainActivity的Presenter 写法1（推荐）
 *
 * 使用了MainScope的协程作用域，另一种写法参照Main2Presenter
 *
 * 关于GlobalScope：
 *      GlobalScope创建的协程没有父协程，
 *      GlobalScope通常也不与任何生命周期组件绑定。
 *      除非手动管理，否则很难满足我们实际开发中的需求。
 *      所以，GlobalScope能不用就尽量不用。
 * @author king
 * @date 2019-11-27 11:27
 */
class MainPresenter(private val mViewBinder: MainViewBinder?) : BasePresenter() {

    //这两个可以声明在IBasePresenter，这里为了区分两种写法，直接在这里声明了
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob) //指定主作用域

    override fun onCreate() {
    }

    override fun onStart() {}

    override fun onStop() {}

    override fun onDestory() {
        //取消协程
        viewModelJob.cancel()
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
