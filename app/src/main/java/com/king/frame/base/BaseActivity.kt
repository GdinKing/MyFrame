package com.king.frame.base

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast

import com.king.frame.R
import com.king.frame.listener.OnBackListener

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * 基础Activity类
 *
 * @author king
 * @date 2019-11-27 10:20
 */
abstract class BaseActivity : AppCompatActivity() {

    var isFullScreen:Boolean = false

    private var mBackListener: OnBackListener? = null

    protected abstract val contentView: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(isFullScreen){
            requestWindowFeature(Window.FEATURE_NO_TITLE)//隐藏标题
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)//设置全屏
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }

    }

    protected abstract fun initView()

    protected abstract fun initData()

    fun showShortToast(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }


    fun setOnBackListener(backListener: OnBackListener) {
        this.mBackListener = backListener
    }


}
