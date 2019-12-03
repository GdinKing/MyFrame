package com.king.frame.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction

import com.king.frame.R
import com.king.frame.base.BaseActivity
import com.king.frame.databinding.ActivityMainBinding
import com.king.frame.ui.fragment.HomeFragment
import com.king.frame.ui.fragment.MyFragment

/**
 * 主界面
 * 简单使用了okttp请求网络数据
 *
 * @author king
 * @date 2019-11-27 10:30
 */
class MainActivity : BaseActivity(){

    private var homeFragment: HomeFragment? = null
    private var myFragment: MyFragment? = null

    private val tabViews = arrayOfNulls<TextView>(2)

    private lateinit var mainActivityBinding:ActivityMainBinding
    override val contentView: Int
        get() = R.layout.activity_main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
        initData()
    }
    /**
     * 初始化视图控件
     */
    override fun initView() {
        tabViews[0] = mainActivityBinding.tab1
        tabViews[1] = mainActivityBinding.tab2
        //默认选中第一个tab
        switchFragment(0)
    }

    override fun initData() {

    }

    /**
     * 切换fragment
     *
     * @param index
     */
    private fun switchFragment(index: Int) {
        val ft = supportFragmentManager!!.beginTransaction()
        hideFragments(ft)
        changeSelect(index)//切换按钮状态
        when (index) {
            0 -> if (homeFragment != null) {
                ft.show(homeFragment!!)
            } else {
                homeFragment = HomeFragment.newInstance()
                ft.add(R.id.fl_container, homeFragment!!, "home")
            }
            1 -> if (myFragment != null) {
                ft.show(myFragment!!)
            } else {
                myFragment = MyFragment.newInstance()
                ft.add(R.id.fl_container, myFragment!!, "my")
            }
        }
        ft.commitAllowingStateLoss()
    }

    /**
     * 隐藏Fragment
     *
     * @param ft
     */
    private fun hideFragments(ft: FragmentTransaction) {
        if (homeFragment != null) {
            ft.hide(homeFragment!!)
        }
        if (myFragment != null) {
            ft.hide(myFragment!!)
        }
    }

    /**
     * 改变tab按钮选中状态
     * @param index
     */
    private fun changeSelect(index: Int) {
        for (i in tabViews.indices) {
            tabViews[i]?.isSelected = false
        }
        tabViews[index]?.isSelected = true
    }



    fun onTab1Click(v:View){
        switchFragment(0)
    }
    fun onTab2Click(v:View){
        switchFragment(1)
    }
}
