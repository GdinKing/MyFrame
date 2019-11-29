package com.king.frame.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.king.frame.base.BaseViewModel
import com.king.frame.model.bean.DataResult
import com.king.frame.model.bean.Result
import com.king.frame.model.bean.FoodMenu
import com.king.frame.config.AppConfig
import com.king.frame.model.HttpHelper
import com.king.frame.utils.LogUtil
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * HomeFragment的ViewModel
 *
 * 使用了viewModelScope的协程作用域,ViewModel组件自动支持
 *
 * @author king
 * @date 2019-11-27 11:27
 */
class HomeViewModel : BaseViewModel() {

    val menuData = MutableLiveData<List<FoodMenu>>()
    val error = MutableLiveData<DataResult>()

    /**
     * 获取数据
     *
     * @param name
     * @param page
     * @param count
     */
    fun getMenuData(name: String, page: Int, count: Int) {
        viewModelScope.launch {
            //主线程（UI线程）
            val deferred = async(Dispatchers.IO) {
                //切换至异步线程执行
                val param = mapOf("key" to AppConfig.JUHE_KEY, "menu" to name, "pn" to page.toString(), "rn" to count.toString())
                HttpHelper.instance.get(AppConfig.FOOD_MENU_URL, param)
            }
            var result = deferred.await()
            if(result.resultCode == 200){
                //请求正常
                val json = result.result
                LogUtil.i("请求结果：$json")

                val data = Gson().fromJson<Result>(json,Result::class.java)
                if(data.resultcode == "200"){
                    //接口数据正常
                    menuData.value = data.result?.data
                }else{
                    error.value = DataResult(data.resultcode!!.toInt(),"", Exception(data.reason))
                }
            }else{
                error.value = result
            }
        }
    }
}
