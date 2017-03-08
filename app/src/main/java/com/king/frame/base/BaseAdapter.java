package com.king.frame.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * RecyclerView的Adapter
 * @author king
 * @date 2016/10/10 16:33
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>  {

    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;
    protected List<T> mDataList = new ArrayList<T>();

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
       holder.bindViewData(position);
    }


    @Override
    public int getItemCount() {
        if(mDataList==null){
            return 0;
        }
        return mDataList.size();
    }

    public abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);


    //item点击事件
    public interface OnItemClickListener{
        void onItemClicked(Object o, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    //长按点击事件
    public interface OnItemLongClickListener{
        void onItemClicked(Object o, int pos);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public void addAll(List<T> dataList){
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void add(T t){
        mDataList.add(t);
        notifyItemInserted(mDataList.size());
    }

    public void remove(int position){
        if(mDataList.size() == 0){
            return;
        }
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    public void onDataChanged(List<T> dataList){
        mDataList = dataList;
        notifyDataSetChanged();
    }
}
