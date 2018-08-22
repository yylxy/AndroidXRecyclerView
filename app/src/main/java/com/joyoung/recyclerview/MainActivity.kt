package com.joyoung.recyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.joyoung.recyclerview.databinding.XTemMainBinding
import com.joyoung.xrecylerview.databinding_adapter.DataBindingBaseAdapter
import com.joyoung.xrecylerview.divider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		//数据源
		val list = ArrayList<String>()
		list.add("123")
		list.add("456")
		list.add("789")
		list.add("001")

		//设置适配器
		val adapter =
			object : DataBindingBaseAdapter<XTemMainBinding, String>(list, R.layout.x_tem_main) {
				override fun setOnBindViewHolder(binding: XTemMainBinding, data: String) {
					binding.textView.text = data
				}
			}
		//添加分隔
		val decorationH = HorizontalDividerItemDecoration.Builder(this)
			.colorResId(R.color.colorPrimary)
			.sizeResId(R.dimen.dp3)
			.showLastDivider()
			.build()
		recyclerView.addItemDecoration(decorationH)
		recyclerView.layoutManager = LinearLayoutManager(this)
		recyclerView.adapter = adapter

		//点击事件
		adapter.setOnItemClick { _, _, _, data ->
			Toast.makeText(this@MainActivity, data.toString(), Toast.LENGTH_SHORT).show()
		}
		//长按事件
		adapter.setOnLongItemClickListener { _, _, _, data ->
			Toast.makeText(this@MainActivity, "$data:$$", Toast.LENGTH_SHORT).show()
		}

	}


}
