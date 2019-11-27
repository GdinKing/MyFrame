package com.king.frame.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import java.util.ArrayList

/**
 * 基础ListView或GridView的Adapter
 *
 * @author king
 * @date 2019-11-27 14:33
 */

abstract class CommonAdapter<T> : BaseAdapter() {

    protected var mContext: Context? = null
    protected var mDataList: MutableList<T>? = ArrayList()

    override fun getCount(): Int {
        return if (mDataList == null) {
            0
        } else mDataList!!.size
    }

    override fun getItem(position: Int): Any? {
        return if (mDataList == null) {
            null
        } else mDataList!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {


        return initView(convertView)
    }

    abstract fun initView(convertView: View): View

    /**
     * 添加
     *
     * @param t
     */
    fun appendChild(t: T) {
        if (mDataList == null) {
            mDataList = ArrayList()
        }
        mDataList!!.add(t)
        notifyDataSetChanged()
    }

    /**
     * 刷新数据
     *
     * @param dataList
     */
    fun refreshData(dataList: MutableList<T>) {
        this.mDataList = dataList
        notifyDataSetChanged()
    }

    /**
     * 添加集合
     *
     * @param dataList
     */
    fun addAll(dataList: List<T>) {
        if (mDataList == null) {
            mDataList = ArrayList()
        }
        mDataList!!.addAll(dataList)
        notifyDataSetChanged()
    }

    /**
     * 删除指定位置的数据
     *
     * @param position
     */
    fun deleteChild(position: Int) {
        if (mDataList != null && mDataList!![position] != null) {
            mDataList!!.removeAt(position)
            notifyDataSetChanged()
        }
    }

    /**
     * 清除所有数据
     */
    fun clearData() {
        if (mDataList != null) {
            mDataList!!.clear()
            notifyDataSetChanged()
        }
    }
}
