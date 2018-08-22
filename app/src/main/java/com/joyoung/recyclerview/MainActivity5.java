package com.joyoung.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.joyoung.xrecylerview.adapter.IWrapperAdapterListener;
import com.joyoung.xrecylerview.adapter.IWrapperAdapterMoreType;
import com.joyoung.xrecylerview.adapter.WrapperAdapter;
import com.joyoung.xrecylerview.adapter.WrapperViewHolder;
import com.joyoung.xrecylerview.divider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.joyoung.recyclerview.DataUtils.getListData;

public class MainActivity5 extends AppCompatActivity {
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
        List<IWrapperAdapterMoreType> mData2 = new ArrayList<>();
        mData2.add(new MyWrapperAdapterMoreType(3, "我是3"));
        mData2.add(new MyWrapperAdapterMoreType(2, "我是2"));
        mData2.add(new MyWrapperAdapterMoreType(1, "我是1"));
        mData2.add(new MyWrapperAdapterMoreType(3, "我是3"));
        mData2.add(new MyWrapperAdapterMoreType(2, "我是2"));
        
        //创建adapter,匿名类的实现方式
        //根据类型显示数据
        WrapperAdapter adapter = new WrapperAdapter(MainActivity5.this) {
            @Override
            public void setItemData(WrapperViewHolder holder, Object object) {
                
                if (object instanceof String) {
                    String da = (String) object;
                    holder.setText(R.id.textView, da);
                    
                } else if (object instanceof MyWrapperAdapterMoreType) {
                    MyWrapperAdapterMoreType da = (MyWrapperAdapterMoreType) object;
                    holder.setText(R.id.textView, da.str);
                }
            }
        };
        
        //点击事件
        adapter.setOnItemClick(new IWrapperAdapterListener.OnItemClick() {
            @Override
            public void click(View view, WrapperViewHolder holder, int position, Object data) {
                Toast.makeText(MainActivity5.this, data.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        
        //长按事件
        adapter.setOnItemLongClick(new IWrapperAdapterListener.OnItemLongClick() {
            @Override
            public void longClick(View view, WrapperViewHolder holder, int position, Object data) {
                Toast.makeText(MainActivity5.this, data.toString() + "$$", Toast.LENGTH_SHORT).show();
            }
        });
        
        //设置布局与数据源-1
        adapter.addHolder(R.layout.x_tem_main_2, mData1);
        //设置布局与数据源-2
        adapter.addMoreHolder(mData2);
        
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
    
    //实现 混合布局的业务接口
    class MyWrapperAdapterMoreType implements IWrapperAdapterMoreType {
        int type;
        String str;
        
        MyWrapperAdapterMoreType(int type, String str) {
            this.type = type;
            this.str = str;
        }
        
        @Override
        public int getItemViewType() {
            return type;
        }
        
        @Override
        public int getItemViewId() {
            if (type == 1) {
                return R.layout.x_tem_main_2;
            } else if (type == 2) {
                return R.layout.x_tem_main_3;
            } else {
                return R.layout.x_tem_main_2;
            }
        }
    }
    
}
