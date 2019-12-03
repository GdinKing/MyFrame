package com.king.frame.model

import com.king.frame.config.AppConfig
import com.king.frame.model.bean.MenuResult
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit网络请求接口
 *
 * @author king
 * @date 2019-12-02 14:26
 */
interface NetworkApi {

    @GET("/cook/query?key=${AppConfig.JUHE_KEY}")
    fun getFoodMenuAsync(@Query("menu") menu:String,
                         @Query("pn") pn:Int, @Query("rn") rn:Int) : Deferred<Response<MenuResult>>

    @GET("/cook/query?key=${AppConfig.JUHE_KEY}")
    fun getFoodMenuStr(@Query("menu") menu:String,
                         @Query("pn") pn:Int, @Query("rn") rn:Int) : Deferred<Response<String>>

}