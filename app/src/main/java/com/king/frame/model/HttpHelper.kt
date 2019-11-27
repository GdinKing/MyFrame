package com.king.frame.model

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.IOException
import java.net.Proxy
import java.net.URLEncoder
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Http请求工具类
 *
 * @author king
 * @date 2019/11/26 13:55
 */
class HttpHelper private constructor() {

    init {
        initOkHttpClient()
    }

    private object SingletonInstance  {//单例
        val INSTANCE = HttpHelper()
    }

    companion object {
        val instance: HttpHelper
            get() = SingletonInstance.INSTANCE

        private var okHttpClient: OkHttpClient? = null

        val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    }

    /**
     * get请求
     *
     * @param url
     * @param params
     * @return
     */
    @Throws(IOException::class)
    fun get(url: String, params: Map<String, String>?): String {
        val request = Request.Builder().url(attachHttpGetParams(url,params)).build()
        val response = okHttpClient!!.newCall(request).execute()
        return response.body!!.string()
    }

    /**
     * post请求
     *
     * @param url
     * @param params
     * @return
     */
    @Throws(IOException::class)
    fun post(url: String, params: Map<String, String>?): String {
        var params = params

        if (params == null) {
            params = HashMap()
        }
        val js = JSONObject(params)
        val body = RequestBody.create(MEDIA_TYPE_JSON, js.toString())
        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()
        val response = okHttpClient!!.newCall(request).execute()
        return response.body!!.string()

    }


    private fun initOkHttpClient() {
        okHttpClient = OkHttpClient()
        okHttpClient!!.newBuilder().connectTimeout(60, TimeUnit.SECONDS).proxy(Proxy.NO_PROXY)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
    }


    /**
     * 拼接Get请求参数
     * @param url
     * @param params
     * @return
     */
    fun attachHttpGetParams(url: String, params: Map<String, String>?): String {

        val keys = params?.keys!!.iterator()
        val values = params?.values!!.iterator()
        val stringBuffer = StringBuffer()
        stringBuffer.append("?")

        for (i in 0 until params.size) {
            var value: String? = null
            try {
                value = URLEncoder.encode(values.next(), "utf-8")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            stringBuffer.append(keys.next() + "=" + value)
            if (i != params.size - 1) {
                stringBuffer.append("&")
            }
        }

        return url + stringBuffer.toString()
    }

}
