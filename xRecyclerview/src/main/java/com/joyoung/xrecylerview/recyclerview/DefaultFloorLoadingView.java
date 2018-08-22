package com.joyoung.xrecylerview.recyclerview;


import com.joyoung.xrecylerview.R;

import static com.joyoung.xrecylerview.recyclerview.XRecyclerViewGlobal.LOADING_TEXT;


public class DefaultFloorLoadingView implements IFloorLoadingView {

    @Override
    public int getItemLayoutId() {
        return R.layout.x_recyclerview_item;
    }

    @Override
    public int getHintTextViewId() {
        return R.id.x_recyclerView_loadingText;
    }

    @Override
    public int getProgressBarViewId() {
        return R.id.x_recyclerView_loadingProgressBar;
    }

    @Override
    public String getLoadingText() {
        return LOADING_TEXT[0];
    }

    @Override
    public String getLoadedText() {
        return LOADING_TEXT[1];
    }


}
