package com.king.frame.base

import android.view.View

import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView通用ViewHolder
 *
 * @author king
 * @date 2019-11-27 11:56
 */

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    init {
        initView()
    }

    abstract fun initView()

    abstract fun bindViewData(pos: Int)
}
