package com.king.frame.service.presenter;

/**
 * 基础的presenter，用于关联Activity或fragment生命周期
 * @author king
 * @date 2017/3/6 11:24
 */
public interface IBasePresenter {

    void onCreate();

    void onStart();

    void onPause();

    void onStop();

    void onDestory();
}
