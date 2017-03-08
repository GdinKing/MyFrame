package com.king.frame.service.model;

import com.king.frame.bean.Book;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * retrofit网络请求接口
 * @author king
 * @date 2017/3/6 10:10
 */

public interface RetrofitService {

    /**
     * 获取book信息 直接实体类
     * @param key
     * @param tag
     * @param page
     * @param count
     * @return
     */
    @GET("book/search")
    Observable<Book> getSearchBook(@Query("q") String key, @Query("tag") String tag,
                                   @Query("start") int page, @Query("count") int count);

    /**
     * 获取book信息 json数据
     * @param key
     * @param tag
     * @param page
     * @param count
     * @return
     */
    @GET("book/search")
    Observable<String> getSearchBookJson(@Query("q") String key, @Query("tag") String tag,
                                   @Query("start") int page, @Query("count") int count);
}
