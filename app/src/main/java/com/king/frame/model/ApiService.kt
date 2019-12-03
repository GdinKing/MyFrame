package com.king.frame.model

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.king.frame.config.AppConfig
import com.king.frame.model.bean.DataResult
import com.king.frame.utils.LogUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit



/**
 * @author king
 * @date 2019-12-02 13:53
 */
object ApiService {

    /**
     * 拦截器
     *
     * 这里可能报错Cannot inline bytecode built with JVM target 1.8
     * 在build.gradle加上 kotlinOptions { jvmTarget = 1.8}
     */
    private var interceptor: Interceptor = Interceptor { chain ->
        val newUrl = chain.request().url
                .newBuilder()
                .addQueryParameter("key", AppConfig.JUHE_KEY)
                .build()

        val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()
        var res = chain.proceed(newRequest)
        LogUtil.i(res.body?.string())//打印日志
        res
    }
    //构建OkHttpClient
    private val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .proxy(Proxy.NO_PROXY)
//            .addInterceptor(interceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

    //构建retrofit
    private fun retrofit(): Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(AppConfig.BASE_API_URL)
//            .addConverterFactory(ScalarsConverterFactory.create())//字符串适配器，直接返回json字符串
            .addConverterFactory(MoshiConverterFactory.create())//moshi解析json适配器，自动将json解析为对象
//            .addConverterFactory(GsonConverterFactory.create())//gson解析json适配器，自动将json解析为对象
            .addCallAdapterFactory(CoroutineCallAdapterFactory())//支持kotlin协程
            .build()

    //创建接口对象
    val api: NetworkApi = retrofit().create(NetworkApi::class.java)

    /**
     * 执行网络请求操作，并处理异常
     *
     * suspend声明只能在协程内执行
     */
    suspend fun <T : Any> execute(call: suspend () -> Response<T>): T? {
        val data = parseApiResult(call)
        var result: T? = null
        when (data) {
            is DataResult.Success -> result = data.result
            is DataResult.Error -> {
                //请求失败
                LogUtil.e(data.errorMessage)
            }
        }
        return result
    }

    /**
     * 处理网络请求结果（包含异常处理）
     */
    private suspend fun <T : Any> parseApiResult(call: suspend () -> Response<T>): DataResult<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                return DataResult.Success(response.body())
            }
            return DataResult.Error(response.code().toString(), response.message())
        }catch (e:Exception){
            LogUtil.e(e)
            return DataResult.Error("", e.message)
        }
    }

}
