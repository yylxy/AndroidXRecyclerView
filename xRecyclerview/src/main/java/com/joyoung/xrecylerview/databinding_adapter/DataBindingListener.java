package com.joyoung.xrecylerview.databinding_adapter;

import android.databinding.ViewDataBinding;
import android.view.View;

public interface DataBindingListener {
    /**
     * 点击事件
     */
    interface OnItemClickListener<B extends ViewDataBinding, D> {
        void click(View view, B binding, int position, D data);
    }
    
    /**
     * 长按事件
     */
    interface OnLongItemClickListener<B extends ViewDataBinding, D> {
        void click(View view, B binding, int position, D data);
    }
    
}
