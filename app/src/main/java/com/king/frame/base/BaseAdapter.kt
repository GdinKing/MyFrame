package com.king.frame.base

import android.view.ViewGroup

import java.util.ArrayList

import androidx.recyclerview.widget.RecyclerView


/**
 * RecyclerView的通用Adapter
 *
 * @author king
 * @date 2019-11-27 14:33
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder>() {

    protected var mOnItemClickListener: OnItemClickListener? = null
    protected var mOnItemLongClickListener: OnItemLongClickListener? = null
    protected var mDataList: MutableList<T>? = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return getViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindViewData(position)
    }


    override fun getItemCount(): Int {
        return if (mDataList == null) {
            0
        } else mDataList!!.size
    }

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder


    //item点击事件
    interface OnItemClickListener {
        fun onItemClicked(o: Any, pos: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    //长按点击事件
    interface OnItemLongClickListener {
        fun onItemClicked(o: Any, pos: Int)
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener
    }

    fun addAll(dataList: List<T>) {
        mDataList!!.addAll(dataList)
        notifyDataSetChanged()
    }

    fun add(t: T) {
        mDataList!!.add(t)
        notifyItemInserted(mDataList!!.size)
    }

    fun remove(position: Int) {
        if (mDataList!!.size == 0) {
            return
        }
        mDataList!!.removeAt(position)
        notifyItemRemoved(position)
    }

    fun onDataChanged(dataList: MutableList<T>) {
        mDataList = dataList
        notifyDataSetChanged()
    }
}
