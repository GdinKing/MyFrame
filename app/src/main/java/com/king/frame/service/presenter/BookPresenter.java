package com.king.frame.service.presenter;

import com.king.frame.service.model.DataManager;
import com.king.frame.service.view.BookViewBinder;
import com.king.frame.utils.LogUtil;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author king
 * @date 2017/3/6 11:27
 */

public class BookPresenter implements IBasePresenter {

    private DataManager mManager;

    private CompositeSubscription mSubscription;

    private BookViewBinder mViewBinder;

    public BookPresenter(BookViewBinder viewBinder) {
        this.mViewBinder = viewBinder;
        mManager = new DataManager();
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestory() {
        //注销监听
        if (mSubscription.hasSubscriptions()) {
            mSubscription.unsubscribe();
        }
    }

    /**
     * 获取数据
     *
     * @param name
     * @param tag
     * @param page
     * @param count
     */
    public void getSearchBookJson(String name, String tag, int page, int count) {
        if (mSubscription == null) {
            return;
        }
        if (mViewBinder != null) {
            mViewBinder.showProgress();//显示加载中对话框
        }

        mSubscription.add(mManager.getSeachBookJson(name, tag, page, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        if (mViewBinder != null) {
                            mViewBinder.hideProgress();//隐藏加载中对话框
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e);
                        if (mViewBinder != null) {
                            mViewBinder.onError("error");
                        }
                    }

                    @Override
                    public void onNext(String json) {
                        if (mViewBinder != null) {
                            mViewBinder.onSuccess(json);
                        }
                    }
                }));
    }
}
