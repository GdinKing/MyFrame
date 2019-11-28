package com.king.frame.ui.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

import com.king.frame.R
import com.king.frame.base.BaseActivity
import com.king.frame.presenter.MainPresenter
import com.king.frame.viewmodel.MainViewBinder
import com.king.frame.utils.LogUtil

/**
 * 主界面
 * 简单使用了okttp请求网络数据
 *
 * @author king
 * @date 2019-11-27 10:30
 */
class MainActivity : BaseActivity(), MainViewBinder {

    private var tv_data: TextView? = null
    private var btn_get: Button? = null

    private var mProgressDialog: ProgressDialog? = null

    //lifecycle（getLifecycle()）在AppCompatActivity已经定义了，直接使用
    private val mMainPresenter = MainPresenter(this,lifecycle)

    override val contentView: Int
        get() = R.layout.activity_main

    override fun initView() {
        tv_data = findViewById(R.id.tv_data)
        btn_get = findViewById(R.id.btn_get)
        mProgressDialog = ProgressDialog(this)
        mProgressDialog!!.setMessage("加载中")
    }

    override fun initData() {

        lifecycle.addObserver(mMainPresenter)//关联起来

        btn_get?.setOnClickListener {
            //获取网络数据
            mMainPresenter.getMenuJson("番茄", 0, 2)
        }
    }

    override fun showProgress() {
        if (mProgressDialog != null && !mProgressDialog!!.isShowing) {
            mProgressDialog!!.show()
        }
    }

    override fun hideProgress() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }

    override fun showBookData(response: String?, error: Exception?) {
        if (error != null) {
            showShortToast(error?.message!!)
            LogUtil.e(error)
            return
        }
        tv_data?.text = response
    }

}
