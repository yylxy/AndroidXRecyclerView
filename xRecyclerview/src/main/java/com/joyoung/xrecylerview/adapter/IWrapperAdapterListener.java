package com.joyoung.xrecylerview.adapter;

import android.view.View;

/**
 * 说明：事件监听
 * 杨阳：360621904 2018/5/30
 */
public interface IWrapperAdapterListener {
    /**
     * 点击事件
     */
    interface OnItemClick {
        void click(View view, WrapperViewHolder holder, int position, Object data);
    }

    /**
     * 长按事件
     */
    interface OnItemLongClick {
        void longClick(View view, WrapperViewHolder holder, int position, Object data);
    }

    /**
     * 加载Item 显示的回调
     */
    interface OnLoadingFloorItemShowCallback {
        void floorItemShowCallback(View view, WrapperViewHolder holder, int position, Object data);
    }


}
