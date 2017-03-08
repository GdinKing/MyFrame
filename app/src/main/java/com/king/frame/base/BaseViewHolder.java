package com.king.frame.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author king
 * @date 2017/3/2 09:56
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder{

    public View itemView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        initView();

    }

    public abstract void initView();

    public abstract void bindViewData(int pos);
}
