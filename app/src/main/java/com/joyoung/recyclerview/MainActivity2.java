package com.joyoung.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.joyoung.recyclerview.databinding.XTemMainBinding;
import com.joyoung.xrecylerview.databinding_adapter.DataBindingBaseAdapter;
import com.joyoung.xrecylerview.databinding_adapter.DataBindingListener;
import com.joyoung.xrecylerview.divider.HorizontalDividerItemDecoration;
import com.joyoung.xrecylerview.divider.VerticalDividerItemDecoration;

import java.util.List;

import static com.joyoung.recyclerview.DataUtils.getListData;
/**
 * 说明：DataBindingAdapter 使用
 * 一束光线：1050189980 2018/8/21
 */
public class MainActivity2 extends AppCompatActivity {
    
    RecyclerView mRecyclerView;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        databindingTest();
    }
    
    
    private void databindingTest() {
        //数据源
        List<String> list = getListData();
        
        //创建adapter,匿名类的实现方式
        DataBindingBaseAdapter adapter = new DataBindingBaseAdapter<XTemMainBinding, String>(list, R.layout.x_tem_main) {
            @Override
            public void setOnBindViewHolder(XTemMainBinding binding, String data) {
                binding.textView.setText(data);
            }
        };
        
        //添加分隔线（横线）
        HorizontalDividerItemDecoration decorationH = new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimary)
                .sizeResId(R.dimen.dp3)
                .showLastDivider()
                .build();
        mRecyclerView.addItemDecoration(decorationH);
        
        //添加点击事件
        adapter.setOnItemClick(new DataBindingListener.OnItemClickListener<XTemMainBinding, String>() {
            @Override
            public void click(View view, XTemMainBinding binding, int position, String data) {
                Toast.makeText(MainActivity2.this, data, Toast.LENGTH_SHORT).show();
            }
        });
        
        //添加长按事件
        adapter.setOnLongItemClickListener(new DataBindingListener.OnLongItemClickListener<XTemMainBinding, String>() {
            @Override
            public void click(View view, XTemMainBinding binding, int position, String data) {
                Toast.makeText(MainActivity2.this, data + "$$", Toast.LENGTH_SHORT).show();
            }
        });
        //设置管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置adapter
        mRecyclerView.setAdapter(adapter);
    }
}
