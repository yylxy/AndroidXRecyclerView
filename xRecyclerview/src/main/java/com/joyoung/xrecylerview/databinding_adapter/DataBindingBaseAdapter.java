package com.joyoung.xrecylerview.databinding_adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused" })
public abstract class DataBindingBaseAdapter<B extends ViewDataBinding, D> extends RecyclerView.Adapter<DataBindingHolder<B>> {
    private List<D> mData;
    private int mLayoutId;
    private DataBindingListener.OnItemClickListener mOnItemClickListener;
    private DataBindingListener.OnLongItemClickListener mOnLongItemClickListener;
    
    public DataBindingBaseAdapter(List<D> data, int layoutId) {
        mData = data;
        mLayoutId = layoutId;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public DataBindingHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()), mLayoutId, viewGroup, false);
        return new DataBindingHolder(binding);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(final DataBindingHolder<B> holder, int position) {
        setOnBindViewHolder(holder.getBinding(), mData.get(position));
        holder.getBinding().executePendingBindings();
        
        if (mOnItemClickListener != null)
            holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.click(holder.getBinding().getRoot(), holder.getBinding(), holder.getLayoutPosition(), mData.get(holder.getLayoutPosition()));
                    holder.getBinding().executePendingBindings();
                }
            });
        if (mOnLongItemClickListener != null)
            holder.getBinding().getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnLongItemClickListener.click(holder.getBinding().getRoot(), holder.getBinding(), holder.getLayoutPosition(), mData.get(holder.getLayoutPosition()));
                    holder.getBinding().executePendingBindings();
                    return false;
                }
            });
    }
    
    @Override
    public int getItemCount() {
        return mData.size();
    }
    
    public void setOnItemClick(DataBindingListener.OnItemClickListener mOnItemClick) {
        this.mOnItemClickListener = mOnItemClick;
    }
    
    public void setOnLongItemClickListener(DataBindingListener.OnLongItemClickListener mOnLongItemClickListener) {
        this.mOnLongItemClickListener = mOnLongItemClickListener;
    }
    
    public abstract void setOnBindViewHolder(B binding, D data);
}
