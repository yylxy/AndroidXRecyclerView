package com.joyoung.xrecylerview.recyclerview;

import android.support.annotation.NonNull;


/**
 * 说明：业务接口
 * 杨阳：360621904 2018/5/29
 */
public interface IXRecyclerViewBiz {
    /**
     * 刷新的监听
     */
    interface RefreshListener {
        void onRefreshListener();
    }
    
    /**
     * 加载的监听
     */
    interface LoadingListener {
        void onLoadingListener();
    }
    
    /**
     * 设置刷新回调
     */
    void setRefreshListener(@NonNull RefreshListener refreshListener);
    
    /**
     * 设置加载回调
     */
    void setLoadingListener(@NonNull LoadingListener loadingListener, IFloorLoadingView... loadingLayoutId);
    
    /**
     * 复位加载与刷新
     */
    void restorationRefreshingOrLoading();
    
    /**
     * 加载完成
     */
    void finishLoading();
    
    
}
