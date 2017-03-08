package com.king.frame.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.king.frame.R;
import com.king.frame.listener.OnBackListener;

/**
 * 基础Activity类
 *
 * @author king
 * @date 2017-03-01 15:20
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    protected Toolbar mToolbar;

    protected TextView tv_right;

    private OnBackListener mBackListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mContext = this;
        initToolBar();
        initView();
        loadData();
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        // 主标题,默认为app_label的名字
        mToolbar.setTitle("");
        //取代原本的actionbar
        setSupportActionBar(mToolbar);
        mToolbar.findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBackListener != null) {
                    mBackListener.onBackClick();
                } else {
                    finish();
                }
            }
        });

        tv_right = (TextView) mToolbar.findViewById(R.id.btn_right);
    }

    /**
     * 设置返回按钮是否可见
     *
     * @param flag
     */
    protected void setBackVisible(boolean flag) {
        mToolbar.findViewById(R.id.tv_back).setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void loadData();

    public void showShortToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }


    public void setOnBackListener(OnBackListener backListener) {
        this.mBackListener = backListener;
    }


}
