package com.king.frame.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.king.frame.base.BaseViewModel
import com.king.frame.model.bean.DataResult
import com.king.frame.model.ApiService
import com.king.frame.model.bean.FoodMenu
import kotlinx.coroutines.*

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
    val error = MutableLiveData<DataResult.Error>()

    /**
     * 获取数据
     *
     * @param name
     * @param page
     * @param count
     */
    fun getMenuData(menu: String, page: Int, count: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            //使用Retrofit接口
            val res = ApiService.execute(
                    call = { ApiService.api.getFoodMenuAsync(menu, page, count).await() })

//            val result = ApiService.execute (call= {
//               ApiService.api.getFoodMenuStr(menu, page, count).await()
//            })
//            LogUtil.i("结果$result")
//            val moshi = Moshi.Builder()
//                    .add(KotlinJsonAdapterFactory())
//                    .build()
//            val adapter = moshi.adapter(MenuResult::class.java)
//            val res = adapter.fromJson(result)

            if(res == null){
                //为空，说明请求失败了
                error.postValue(DataResult.Error("400", "Network Error"))
                return@launch
            }

            //请求成功
            if (res?.resultcode == "200") {
                //接口数据正常
                menuData.postValue(res?.result?.data)
            } else {
                error.postValue(DataResult.Error(res?.resultcode, res?.reason))
            }
        }
    }


}
