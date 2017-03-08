package com.king.frame.service.model;

import com.king.frame.bean.Book;

import rx.Observable;

/**
 * @author king
 * @date 2017/3/6 11:17
 */

public class DataManager {

    private RetrofitService mService;

    public DataManager(){
        this.mService = RetrofitHelper.getInstance().getService();
    }

    /**
     * 获取搜索书籍的Observable
     * @param name
     * @param tag
     * @param page
     * @param count
     * @return
     */
    public Observable<Book> getSeachBook(String name,String tag,int page,int count){
        return mService.getSearchBook(name,tag,page,count);
    }

    public Observable<String> getSeachBookJson(String name,String tag,int page,int count){
        return mService.getSearchBookJson(name,tag,page,count);
    }
}
