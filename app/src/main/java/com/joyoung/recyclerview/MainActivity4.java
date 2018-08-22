package com.joyoung.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.joyoung.xrecylerview.adapter.IWrapperAdapterListener;
import com.joyoung.xrecylerview.adapter.WrapperAdapter;
import com.joyoung.xrecylerview.adapter.WrapperViewHolder;
import com.joyoung.xrecylerview.divider.HorizontalDividerItemDecoration;
import com.joyoung.xrecylerview.divider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.joyoung.recyclerview.DataUtils.getListData;

public class MainActivity4 extends AppCompatActivity {
    RecyclerView mRecyclerView;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        Test();
        
    }
    
    private void Test() {
        //数据源 1
        List<String> mData1 = getListData();
        //数据源 2
        List<String> mData2 = new ArrayList<>();
        mData2.add("1111");
        mData2.add("2222");
        mData2.add("3333");
        mData2.add("4444");
        mData2.add("5555");
        
        //创建adapter,匿名类的实现方式
        WrapperAdapter adapter = new WrapperAdapter(MainActivity4.this) {
            @Override
            public void setItemData(WrapperViewHolder holder, Object object) {
                String da = (String) object;
                holder.setText(R.id.textView, da);
            }
        };
        
        //点击事件
        adapter.setOnItemClick(new IWrapperAdapterListener.OnItemClick() {
            @Override
            public void click(View view, WrapperViewHolder holder, int position, Object data) {
                Toast.makeText(MainActivity4.this, data.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        
        //长按事件
        adapter.setOnItemLongClick(new IWrapperAdapterListener.OnItemLongClick() {
            @Override
            public void longClick(View view, WrapperViewHolder holder, int position, Object data) {
                Toast.makeText(MainActivity4.this, data.toString() + "$$", Toast.LENGTH_SHORT).show();
            }
        });
        
        //设置布局与数据源-1
        adapter.addHolder(R.layout.x_tem_main_2, mData1);
        //设置布局与数据源-2
        adapter.addHolder(R.layout.x_tem_main_3, mData2);
        
        //添加分隔线（横线）
        HorizontalDividerItemDecoration decorationH = new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimary)
                .sizeResId(R.dimen.dp3)
                .showLastDivider()
                .build();
        mRecyclerView.addItemDecoration(decorationH);
        
        //设置管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置adapter
        mRecyclerView.setAdapter(adapter);
        
    }
    
    
}
