package com.king.frame.ui;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.king.frame.R;
import com.king.frame.base.BaseActivity;
import com.king.frame.service.presenter.BookPresenter;
import com.king.frame.service.view.BookViewBinder;
import com.king.frame.utils.LogUtil;

public class MainActivity extends BaseActivity implements BookViewBinder {


    private TextView tv_data;
    private Button btn_get;

    private ProgressDialog mProgressDialog;

    private BookPresenter mBookPresenter = new BookPresenter(this);

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tv_data = (TextView) findViewById(R.id.tv_data);
        btn_get = (Button) findViewById(R.id.btn_get);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("加载中");

    }

    @Override
    protected void loadData() {
        if (mBookPresenter != null) {
            mBookPresenter.onCreate();
        }
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(NetUrl.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
//                .build();
//        RetrofitService service = retrofit.create(RetrofitService.class);
//        Observable<Book> observable = service.getSearchBook("金瓶梅","",0,1);
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Book>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Book book) {
//
//                    }
//                });

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBookPresenter.getSearchBookJson("西游记", "", 0, 1);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mBookPresenter != null) {
            mBookPresenter.onStart();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBookPresenter != null) {
            mBookPresenter.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBookPresenter != null) {
            mBookPresenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBookPresenter != null) {
            mBookPresenter.onDestory();
        }
    }

    @Override
    public void showProgress() {
        if(mProgressDialog!=null&&!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if(mProgressDialog!=null&&mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public String getBookName() {
        //Do something
        return null;
    }

    @Override
    public void onSuccess(String s) {
        tv_data.setText(s);
    }

    @Override
    public void onError(String error) {
        showShortToast(error);
        LogUtil.e(error);
    }
}
