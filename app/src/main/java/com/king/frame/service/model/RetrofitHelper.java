package com.king.frame.service.model;

import com.google.gson.GsonBuilder;
import com.king.frame.config.NetUrl;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Retrofit工具类
 * @author king
 * @date 2017/3/6 11:11
 */

public class RetrofitHelper {
    private OkHttpClient client = new OkHttpClient();
    private GsonConverterFactory gsonFactory = GsonConverterFactory.create(new GsonBuilder().create());//直接解析json为实体类
    private ScalarsConverterFactory stringFactory = ScalarsConverterFactory.create();//返回json字符串
    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit = null;

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            instance = new RetrofitHelper();
        }
        return instance;
    }

    private RetrofitHelper() {
        init();
    }

    //初始化Retrofit
    private void init() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(NetUrl.BASE_URL)
                .client(client)
                .addConverterFactory(stringFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit(){

        return mRetrofit;
    }
    public RetrofitService getService() {
        return mRetrofit.create(RetrofitService.class);
    }

}
