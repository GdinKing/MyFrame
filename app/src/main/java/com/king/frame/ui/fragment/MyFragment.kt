package com.king.frame.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.king.frame.R
import com.king.frame.base.BaseFragment
import com.king.frame.databinding.FragmentMyBinding
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

    private lateinit var fragmentMyBinding: FragmentMyBinding

    override fun initView() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentMyBinding = DataBindingUtil.inflate(inflater,contentView,container,false)
        initView()
        return fragmentMyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel = ViewModelProviders.of(this, MyViewModel.Factory("当前时间：")).get(MyViewModel::class.java)
        myViewModel?.timeData?.observe(this, Observer {
            fragmentMyBinding.time = it
        })

    }
}