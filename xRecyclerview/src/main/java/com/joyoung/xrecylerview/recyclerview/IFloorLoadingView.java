package com.joyoung.xrecylerview.recyclerview;

/**
 * 说明：底部加载view
 * 杨阳：360621904 2018/6/1
 */
public interface IFloorLoadingView {
    /**
     * 布局的id
     */
    int getItemLayoutId();

    /**
     * 提示文本TextView Id
     */
    int getHintTextViewId();


    /**
     * 提示进度View Id
     */
    int getProgressBarViewId();

    /**
     * 加载中提示文本
     */
    String getLoadingText();

    /**
     * 全部加载完成的提示文本
     */
    String getLoadedText();

}
