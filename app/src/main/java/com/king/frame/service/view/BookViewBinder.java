package com.king.frame.service.view;

/**
 * book界面的UI操作接口
 * @author king
 * @date 2017/3/7 14:56
 */

public interface BookViewBinder extends IViewBinder {

    void showProgress();//显示进度条

    void hideProgress();//隐藏进度条

    String getBookName();//获取书籍名称

    void onSuccess(String response);//请求成功

    void onError(String error);//请求失败
}
