package com.king.frame.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础ListView或GridView的Adapter
 *
 * @author king
 * @date 2017/3/2 09:57
 */

public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDataList = new ArrayList<>();

    @Override
    public int getCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        if (mDataList == null) {
            return null;
        }
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        return initView(convertView);
    }

    public abstract View initView(View convertView);

    /**
     * 添加
     *
     * @param t
     */
    public void appendChild(T t) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataList.add(t);
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     *
     * @param dataList
     */
    public void refreshData(List<T> dataList) {
        this.mDataList = dataList;
        notifyDataSetChanged();
    }

    /**
     * 添加集合
     *
     * @param dataList
     */
    public void addAll(List<T> dataList) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    /**
     * 删除指定位置的数据
     *
     * @param position
     */
    public void deleteChild(int position) {
        if (mDataList != null && mDataList.get(position) != null) {
            mDataList.remove(position);
            notifyDataSetChanged();
        }
    }

    /**
     * 清除所有数据
     */
    public void clearData() {
        if (mDataList != null) {
            mDataList.clear();
            notifyDataSetChanged();
        }
    }
}
