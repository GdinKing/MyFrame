package com.king.frame.base

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment

/**
 * 基础Fragment
 *
 * @author king
 * @date 2019-11-27 14:30
 */
abstract class BaseFragment : Fragment() {

    protected var mContentView: View? = null

    protected abstract val contentView: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mContentView = inflater.inflate(contentView, container, false)
        initView()
        return mContentView
    }

    protected abstract fun initView()



    fun showShortToast(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(activity!!, msg, Toast.LENGTH_SHORT).show()
        }
    }

}
