package com.joyoung.xrecylerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joyoung.xrecylerview.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



/**
 * 说明：RecyclerView 包裹Adapter
 * 杨阳：360621904 2018/5/30
 */
@SuppressWarnings({"unchecked", "unused", "NullableProblems"})
public abstract class WrapperAdapter extends RecyclerView.Adapter<WrapperViewHolder> {
    private IWrapperAdapterListener.OnItemClick mOnItemClick;
    private IWrapperAdapterListener.OnItemLongClick mOnItemLongClick;
    private Map<ItemType, List> mTypeList;
    private LayoutInflater inflater;
    private MyListenerImp myListenerImp;
    private RecyclerView.ViewHolder holder;
    private RecyclerView mRecyclerView;
    /**item 的UI 存储需要跨列的类型（目前是加载类型）*/
    private List<Integer> mItemTypeData;

    @SuppressWarnings("WeakerAccess")
    public WrapperAdapter(@NonNull Context context) {
        mTypeList = new LinkedHashMap<>();
        this.inflater = LayoutInflater.from(context);
        myListenerImp = new MyListenerImp();
        mItemTypeData = new ArrayList<>();
    }

    /**
     * 添加单布局holder
     *
     * @param LayoutId 布局Id
     * @param data     数据源
     */
    public void addHolder(int LayoutId, @NonNull List data) {
        mTypeList.put(new ItemType(LayoutId), data);
    }

    /**
     * 添加多布局holder，实体对象必须实现 {@link IWrapperAdapterMoreType}这个接口
     *
     * @param data 数据源
     */
    public void addMoreHolder(@NonNull List<IWrapperAdapterMoreType> data) {
        mTypeList.put(new ItemType(IWrapperAdapterMoreType.class.hashCode()), data);
    }

    public void addFloorLoadingHolder(int layoutId, @NonNull List<IWrapperAdapterFloorType> data,
                                      IWrapperAdapterListener.OnLoadingFloorItemShowCallback onLoadingFloorItemShowCallback) {
        mTypeList.put(new ItemTypeTwo(layoutId, onLoadingFloorItemShowCallback), data);
        mItemTypeData.add(layoutId);
    }


    /**
     * 设置item 的数据
     *
     * @param holder item 对应的Holder
     * @param object item 对应的数据对象
     */
    public abstract void setItemData(WrapperViewHolder holder, Object object);

    public void setOnItemClick(IWrapperAdapterListener.OnItemClick onItemLick) {
        mOnItemClick = onItemLick;
    }

    public void setOnItemLongClick(IWrapperAdapterListener.OnItemLongClick onItemLongClick) {
        mOnItemLongClick = onItemLongClick;
    }

    @Override
    public WrapperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WrapperViewHolder(inflater.inflate(viewType, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(final WrapperViewHolder holder, final int position) {
        setItemData(holder, getValue(position));
        Object object = getValue(position);
        //把holder,position,data 添加到view tag 中
        holder.itemView.setTag(R.id.tag_key_holder, holder);
        holder.itemView.setTag(R.id.tag_key_position, holder.getLayoutPosition());
        holder.itemView.setTag(R.id.tag_key_data, object);
        if (mOnItemClick != null && !(object instanceof IWrapperAdapterFloorType))
            holder.itemView.setOnClickListener(myListenerImp);
        if (mOnItemLongClick != null)
            holder.itemView.setOnLongClickListener(myListenerImp);
        if (mOnItemClick != null || mOnItemLongClick != null)
            holder.setItemBackground();
        //判断是否是加载的itemView
        if (object instanceof IWrapperAdapterFloorType) {
            ItemTypeTwo typeTwo = (ItemTypeTwo) getKey(position);
            assert typeTwo != null;
            if (typeTwo.mOnLoadingFloorItemShowCallback != null)
                typeTwo.mOnLoadingFloorItemShowCallback.floorItemShowCallback(holder.itemView, holder, holder.getLayoutPosition(), object);
        }
    }

    @Override
    public int getItemCount() {
        return getSumCount();
    }

    @Override
    public int getItemViewType(int position) {
        return getMyItemViewType(position);
    }

    @Override
    public void onViewAttachedToWindow(WrapperViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        // 处理StaggeredGridLayoutManager，设置充满整行
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (null != layoutParams && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            if (mItemTypeData.contains(mRecyclerView.getAdapter().getItemViewType(holder.getLayoutPosition())))
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
        }
    }


    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        // 处理GridLayoutManager，设置充满整行
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mItemTypeData.contains(mRecyclerView.getAdapter().getItemViewType(position))) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    /**
     * 事件回调的处理
     */
    class MyListenerImp implements View.OnClickListener, View.OnLongClickListener {
        @Override
        public void onClick(View view) {
            mOnItemClick.click(view, (WrapperViewHolder) view.getTag(R.id.tag_key_holder), (int) view.getTag(R.id.tag_key_position), view.getTag(R.id.tag_key_data));
        }

        @Override
        public boolean onLongClick(View view) {
            mOnItemLongClick.longClick(view, (WrapperViewHolder) view.getTag(R.id.tag_key_holder), (int) view.getTag(R.id.tag_key_position), view.getTag(R.id.tag_key_data));
            return false;
        }
    }

    /**
     * 总条目数
     */
    private int getSumCount() {
        int sumCount = 0;
        for (Map.Entry entry : mTypeList.entrySet()) {
            if (entry.getValue() != null)
                sumCount += ((List) entry.getValue()).size();
        }
        return sumCount;
    }

    /**
     * 条目UI 类型
     */
    private int getMyItemViewType(int position) {
        int count = 0;
        for (Map.Entry entry : mTypeList.entrySet()) {
            List list = (List) entry.getValue();
            //多布局
            if (list.size()>0&&list.get(0) instanceof IWrapperAdapterMoreType && ((count + list.size()) > position)) {
                int moreCount = count;
                for (int i = 0; i < list.size(); i++) {
                    if (moreCount == position) {
                        return ((IWrapperAdapterMoreType) list.get(i)).getItemViewId();
                    }
                    moreCount++;
                }
            }
            //统一布局
            count += ((List) entry.getValue()).size();
            if (count > position) {
                return ((ItemType) entry.getKey()).layoutId;
            }
        }
        return -1;
    }

    private ItemType getKey(int position) {
        int sumCount = 0;
        for (Map.Entry entry : mTypeList.entrySet()) {
            sumCount += ((List) entry.getValue()).size();
            if (sumCount > position) {
                return (ItemType) entry.getKey();
            }
        }
        return null;
    }

    /**
     * 获取数据对象
     */
    private Object getValue(int position) {
        int sumCount = 0;
        for (Map.Entry entry : mTypeList.entrySet()) {
            for (int i = 0; i < ((List) entry.getValue()).size(); i++) {
                if (sumCount == position) {
                    return ((List) entry.getValue()).get(i);
                }
                sumCount++;
            }
        }
        return null;
    }

    /**
     * 布局的类型
     */
    class ItemType {
        //布局的id
        int layoutId;

        ItemType(int layoutId) {
            this.layoutId = layoutId;
        }
    }

    class ItemTypeTwo extends ItemType {
        IWrapperAdapterListener.OnLoadingFloorItemShowCallback mOnLoadingFloorItemShowCallback;

        ItemTypeTwo(int layoutId, IWrapperAdapterListener.OnLoadingFloorItemShowCallback callback) {
            super(layoutId);
            this.mOnLoadingFloorItemShowCallback = callback;
        }
    }

}
