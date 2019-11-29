package com.king.frame.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.king.frame.R
import com.king.frame.base.BaseFragment
import com.king.frame.model.NetLiveData
import com.king.frame.utils.LogUtil
import com.king.frame.viewmodel.HomeViewModel

/**
 * 首页
 *
 * @author king
 * @date 2019-11-29 09:31
 */
class HomeFragment : BaseFragment() {


    override val contentView
        get() = R.layout.fragment_home

    private var tvData: TextView? = null
    private var btnGet: Button? = null
    private var mProgressDialog: ProgressDialog? = null

    private var homeViewModel: HomeViewModel? = null

    //当前数据开始下标
    private var currentIndex = 0
    //每页数据大小
    private var pageCount = 5

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun initView() {
        mProgressDialog = ProgressDialog(activity)
        mProgressDialog?.setMessage("加载中")
        tvData = mContentView?.findViewById(R.id.tv_data)
        btnGet = mContentView?.findViewById(R.id.btn_get)

        btnGet?.setOnClickListener {
            mProgressDialog?.show()
            //获取网络数据
            homeViewModel?.getMenuData("家常菜", currentIndex, pageCount)

        }
    }
    //监听网络变化
    private fun getNetworkStatus(){
        NetLiveData.getInstance(activity!!.applicationContext).observe(this, Observer {
            LogUtil.i("网络是否连接：${it.isConnected}")
            LogUtil.i("网络类型：${it.type}")
            LogUtil.i("网络类型：${it.typeName}")
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNetworkStatus()

        homeViewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]
        //监听menuData的数据变化
//        homeViewModel?.menuData?.observe(this,Observer<List<FoodMenu>>{foodList->{
//        } })
        homeViewModel?.menuData?.observe(this, Observer {

            mProgressDialog?.hide()
            if (it.isNullOrEmpty()) {
                //展示空视图或其他操作

            } else {
                currentIndex += pageCount
                for (menu in it) {
                    tvData?.append("\n${menu.title}")
                }
            }
        })
        //监听error的数据变化
        homeViewModel?.error?.observe(this, Observer {
            mProgressDialog?.hide()
            showShortToast("加载数据失败")
            tvData?.text = "状态码：${it.resultCode}，异常信息:${it.error?.message}"
            LogUtil.e(it.error)
        })

    }

}