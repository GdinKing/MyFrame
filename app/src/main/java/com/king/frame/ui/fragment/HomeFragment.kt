package com.king.frame.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.king.frame.R
import com.king.frame.base.BaseFragment
import com.king.frame.databinding.FragmentHomeBinding
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

    private var mProgressDialog: ProgressDialog? = null

    private var homeViewModel: HomeViewModel? = null

    //当前数据开始下标
    private var currentIndex = 0
    //每页数据大小
    private var pageCount = 5

    //当前Fragment的DataBinding对象
    private lateinit var fragmentBinding:FragmentHomeBinding

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun initView() {
        mProgressDialog = ProgressDialog(activity)
        mProgressDialog?.setMessage("加载中")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = DataBindingUtil.inflate(inflater, contentView, container, false)
        initView()
        fragmentBinding.homeEvent = HomeEvent()//注册监听事件
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]
        //监听menuData的数据变化
        homeViewModel?.menuData?.observe(this, Observer {

            mProgressDialog?.hide()
            if (it.isNullOrEmpty()) {
                //展示空视图或其他操作

            } else {
                currentIndex += pageCount
                fragmentBinding?.list = it
            }
        })
        //监听error的数据变化
        homeViewModel?.error?.observe(this, Observer {
            mProgressDialog?.hide()
            showShortToast("加载数据失败")
            fragmentBinding.tvData?.text = "状态码：${it.errorCode}，异常信息:${it.errorMessage}"
            LogUtil.e(it.errorMessage)
        })

    }

    inner class HomeEvent{

        //点击事件
        fun getDataClick(){
            mProgressDialog?.show()
            //获取网络数据
            homeViewModel?.getMenuData("家常菜", currentIndex, pageCount)
        }
    }
}