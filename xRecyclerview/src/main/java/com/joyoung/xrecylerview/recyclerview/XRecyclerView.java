package com.joyoung.xrecylerview.recyclerview;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joyoung.xrecylerview.R;
import com.joyoung.xrecylerview.adapter.IWrapperAdapterFloorType;
import com.joyoung.xrecylerview.adapter.IWrapperAdapterListener;
import com.joyoung.xrecylerview.adapter.WrapperAdapter;
import com.joyoung.xrecylerview.adapter.WrapperViewHolder;

import java.util.ArrayList;
import java.util.List;


import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.joyoung.xrecylerview.recyclerview.XRecyclerViewGlobal.getRefreshColor;

/**
 * 说明：带刷新与加载的RecyclerView
 * 杨阳：360621904 2018/5/29
 */
@SuppressWarnings("unused")
public class XRecyclerView extends RelativeLayout implements IXRecyclerViewBiz {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private MyScrollAndRefreshListener mMyScrollAndRefreshListener;
    private RefreshListener mRefreshListener;
    private LoadingListener mLoadingListener;
    private IFloorLoadingView mDefaultFloorLoadingView;
    /**
     * 底加载view
     */
    private View mFloorLoadingView;
    /**
     * 是否在加载或刷新中
     */
    private boolean isLoadingOrRefresh;
    /**
     * 是否加载全部完成
     */
    private boolean isLoadingFinish;
    
    public XRecyclerView(Context context) {
        this(context, null);
    }
    
    public XRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public XRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.x_recyclerview, this, true);
        mDefaultFloorLoadingView = new DefaultFloorLoadingView();
        init();
    }
    
    private void init() {
        mRecyclerView = findViewById(R.id.x_recyclerView_RecyclerView);
        mSwipeRefreshLayout = findViewById(R.id.x_recyclerView_swipeRefreshLayout);
        mSwipeRefreshLayout.setEnabled(false);
        setColorSchemeColors(getRefreshColor());
    }
    
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }
    
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //  说明：系统控件的代理方法     2018/6/1
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setColorSchemeColors(@ColorInt int... colors) {
        mSwipeRefreshLayout.setColorSchemeColors(colors);
    }
    
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
        
    }
    
    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mRecyclerView.setLayoutManager(layout);
    }
    
    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }
    
    public void addItemDecoration(RecyclerView.ItemDecoration decor, int index) {
        mRecyclerView.addItemDecoration(decor, index);
    }
    
    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        mRecyclerView.addItemDecoration(decor);
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //  说明：业务方法     2018/6/1
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void setRefreshListener(@NonNull RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(getMyScrollAndRefreshListener());
    }
    
    @Override
    public void setLoadingListener(@NonNull LoadingListener loadingListener, IFloorLoadingView... floorLoadingView) {
        mLoadingListener = loadingListener;
        mRecyclerView.addOnScrollListener(getMyScrollAndRefreshListener());
        
        if (mRecyclerView.getAdapter() instanceof WrapperAdapter) {
            WrapperAdapter adapter = (WrapperAdapter) mRecyclerView.getAdapter();
            if (floorLoadingView != null && floorLoadingView.length > 0) {
                mDefaultFloorLoadingView = floorLoadingView[0];
            }
            List<IWrapperAdapterFloorType> list1 = new ArrayList<>();
            list1.add(new IWrapperAdapterFloorType() {
            });
            adapter.addFloorLoadingHolder(mDefaultFloorLoadingView.getItemLayoutId(), list1, new IWrapperAdapterListener.OnLoadingFloorItemShowCallback() {
                @Override
                public void floorItemShowCallback(View view, WrapperViewHolder holder, int position, Object data) {
                    mFloorLoadingView = view;
                    holder.setText(mDefaultFloorLoadingView.getHintTextViewId(), !isLoadingFinish ?
                            mDefaultFloorLoadingView.getLoadingText() : mDefaultFloorLoadingView.getLoadedText());
                    holder.setVisible(mDefaultFloorLoadingView.getProgressBarViewId(), !isLoadingFinish);
                }
            });
        }
    }
    
    @Override
    public void restorationRefreshingOrLoading() {
        isLoadingOrRefresh = false;
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    
    @Override
    public void finishLoading() {
        isLoadingFinish = true;
        ((TextView) mFloorLoadingView.findViewById(mDefaultFloorLoadingView.getHintTextViewId())).setText(mDefaultFloorLoadingView.getLoadedText());
        mFloorLoadingView.findViewById(mDefaultFloorLoadingView.getProgressBarViewId()).setVisibility(GONE);
    }
    
    /**
     * 获取监听器
     */
    private MyScrollAndRefreshListener getMyScrollAndRefreshListener() {
        if (mMyScrollAndRefreshListener == null) {
            mMyScrollAndRefreshListener = new MyScrollAndRefreshListener();
        }
        return mMyScrollAndRefreshListener;
    }
    
    /**
     * 滑动与刷新监听
     */
    class MyScrollAndRefreshListener extends RecyclerView.OnScrollListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.e("滑动加载", "----" + recyclerView.canScrollVertically(1) + "   newState  " + newState + "   ***   " + recyclerView.canScrollVertically(-1));
            //判断是否到底部，并且滑动已停止
            if (!recyclerView.canScrollVertically(1) && newState == SCROLL_STATE_IDLE && recyclerView.canScrollVertically(-1)) {
                if (mLoadingListener != null && !isLoadingOrRefresh && !isLoadingFinish) {
                    isLoadingOrRefresh = true;
                    mLoadingListener.onLoadingListener();
                }
            }
        }
        
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
        
        @Override
        public void onRefresh() {
            if (mRefreshListener != null) {
                isLoadingOrRefresh = true;
                isLoadingFinish = false;
                mRefreshListener.onRefreshListener();
            }
        }
    }
    
    /**
     * 注册adapter监听器
     * adapter.registerAdapterDataObserver(mDataObserver);
     * mDataObserver.onChanged();
     * <p>
     * 暂不使用
     */
    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        
        @Override
        public void onChanged() {
        }
        
        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
        }
        
        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        }
        
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
        }
        
        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
        }
        
        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        }
    };
    
}
