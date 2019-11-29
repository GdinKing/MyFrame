package com.king.frame.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.king.frame.R
import com.king.frame.base.BaseFragment
import com.king.frame.viewmodel.MyViewModel

/**
 * 我的
 *
 * @author king
 * @date 2019-11-29 09:34
 */
class MyFragment : BaseFragment(){
    override val contentView: Int
        get() = R.layout.fragment_my

    companion object {
        fun newInstance(): MyFragment {
            return MyFragment()
        }
    }

    private var myViewModel:MyViewModel?= null

    private var tvTime:TextView? = null

    override fun initView() {
        tvTime = mContentView?.findViewById(R.id.tv_time)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel = ViewModelProviders.of(this, MyViewModel.Factory("当前时间：")).get(MyViewModel::class.java)
        myViewModel?.timeData?.observe(this, Observer {
            tvTime?.text = it
        })

    }
}