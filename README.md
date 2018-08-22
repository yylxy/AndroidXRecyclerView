## RecyclerView（RecyclerView）

> 使用说明：
**添加引用**

```gradle

    implementation libs.xRecyclerView
    或
    implementation "com.joyoung.smartrobot:smartrobot-xrecylerview:1.0.0-SNAPSHOT"

```

**- 分隔线 的使用**
```java
        //添加分隔线（竖线）
        VerticalDividerItemDecoration decoration = new VerticalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimary)
                .sizeResId(R.dimen.dp3)
                .build();
        mRecyclerView.addItemDecoration(decoration);

        //添加分隔线（横线）
        HorizontalDividerItemDecoration decorationH = new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimary)
                .sizeResId(R.dimen.dp3)
                .showLastDivider()
                .build();
        mRecyclerView.addItemDecoration(decorationH);

```
**- DataBindingAdapter 的使用**

```java

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

```


**- WrapperAdapter 的使用（单一Item）**

```java
     //数据源
            List<String> mData1 = getListData();

            //创建adapter,匿名类的实现方式
            WrapperAdapter  adapter = new WrapperAdapter(MainActivity3.this) {
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
                    Toast.makeText(MainActivity3.this, data.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            //长按事件
            adapter.setOnItemLongClick(new IWrapperAdapterListener.OnItemLongClick() {
                @Override
                public void longClick(View view, WrapperViewHolder holder, int position, Object data) {
                    Toast.makeText(MainActivity3.this, data.toString() + "$$", Toast.LENGTH_SHORT).show();
                }
            });

            //设置布局与数据源
            adapter.addHolder(R.layout.x_tem_main_2, mData1);

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
```


**- 多item Adapter 使用(统一数据类型)**
```java
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

```

**- 多item Adapter 使用(混合布局)**
```java

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
```

**- 刷新与加载的RecyclerView**
- 在xml中的使用
```xml
    <com.joyoung.xrecylerview.recyclerview.XRecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    </com.joyoung.xrecylerview.recyclerview.XRecyclerView>

```
    - 下拉刷新监听

   ```java
           //刷新
           mRecyclerView.setRefreshListener(new IXRecyclerViewBiz.RefreshListener() {
               @Override
               public void onRefreshListener() {
                   //清楚原数据
                    ***...
                   //设置新数据
                     ***...
                   //刷新
                    ***...
                   //调用复位
                   mRecyclerView.restorationRefreshingOrLoading();
               }
           });
   ```
   - 加载监听

   ```java
      //加载

          mRecyclerView.setLoadingListener(new IXRecyclerViewBiz.LoadingListener() {
              @Override
              public void onLoadingListener() {
                 //设置新数据
                 ***...
                 //刷新
                 ***...
                 //调用复位
                 mRecyclerView.restorationRefreshingOrLoading();

                  //条件不满足时，调用完成加载完成。
                  if (条件) {
                      //完成加载
                      mRecyclerView.finishLoading();
                  }
                  Log.e("AAA", "加载");
              }
          });
   ```