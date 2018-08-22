package com.joyoung.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.joyoung.xrecylerview.adapter.IWrapperAdapterFloorType;
import com.joyoung.xrecylerview.adapter.IWrapperAdapterListener;
import com.joyoung.xrecylerview.adapter.WrapperAdapter;
import com.joyoung.xrecylerview.adapter.WrapperViewHolder;
import com.joyoung.xrecylerview.divider.HorizontalDividerItemDecoration;
import com.joyoung.xrecylerview.recyclerview.IXRecyclerViewBiz;
import com.joyoung.xrecylerview.recyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.joyoung.recyclerview.DataUtils.getListData;

public class MainActivity6 extends AppCompatActivity {
    XRecyclerView mRecyclerView;
    List<String> mData1;
    WrapperAdapter adapter;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6);
        mRecyclerView = findViewById(R.id.recyclerView);
        Test();
        
    }
    
    int count;
    
    private void Test() {
        //数据源
        mData1 = new ArrayList<>();
        getListData(mData1);
      
        //创建adapter,匿名类的实现方式
        adapter = new WrapperAdapter(MainActivity6.this) {
            @Override
            public void setItemData(WrapperViewHolder holder, Object object) {
                if (object instanceof String) {
                    String da = (String) object;
                    holder.setText(R.id.textView, da);
                } else {
                    Log.e("AAA", object.toString());
                }
            }
        };
        
        //点击事件
        adapter.setOnItemClick(new IWrapperAdapterListener.OnItemClick() {
            @Override
            public void click(View view, WrapperViewHolder holder, int position, Object data) {
                Toast.makeText(MainActivity6.this, data.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        
        //长按事件
        adapter.setOnItemLongClick(new IWrapperAdapterListener.OnItemLongClick() {
            @Override
            public void longClick(View view, WrapperViewHolder holder, int position, Object data) {
                Toast.makeText(MainActivity6.this, data.toString() + "$$", Toast.LENGTH_SHORT).show();
            }
        });
        
        //设置布局与数据源
        adapter.addHolder(R.layout.x_tem_main_2, mData1);
        
        //添加分隔线（横线）
        HorizontalDividerItemDecoration decorationH = new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimary)
                .sizeResId(R.dimen.dp3)
                .build();
        mRecyclerView.addItemDecoration(decorationH);
        
        //设置管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置adapter
        mRecyclerView.setAdapter(adapter);
        
        //刷新
        mRecyclerView.setRefreshListener(new IXRecyclerViewBiz.RefreshListener() {
            @Override
            public void onRefreshListener() {
                count = 0;
                mData1.clear();
                getListData(mData1);
                adapter.notifyDataSetChanged();
                Log.e("AAA", "刷新");
                //调用复位
                mRecyclerView.restorationRefreshingOrLoading();
                
            }
        });
        
        //加载
        mRecyclerView.setLoadingListener(new IXRecyclerViewBiz.LoadingListener() {
            @Override
            public void onLoadingListener() {
                getListData(mData1);
                adapter.notifyDataSetChanged();
                //调用复位
                mRecyclerView.restorationRefreshingOrLoading();
                count++;
                //条件不满足时，调用完成加载
                if (count == 3) {
                    //完成加载
                    mRecyclerView.finishLoading();
                }
                Log.e("AAA", "加载");
            }
        });
    }
    
    
}
