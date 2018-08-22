package com.joyoung.recyclerview

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_demo)

		//dataBinding Adapter 使用
		button_1.setOnClickListener {
			startActivity(Intent(this, MainActivity2::class.java))
		}

		//单item Adapter 使用
		button_2.setOnClickListener {
			startActivity(Intent(this, MainActivity3::class.java))
		}

		//多item Adapter 使用(统一数据类型)
		button_3.setOnClickListener {
			startActivity(Intent(this, MainActivity4::class.java))
		}

		//多item Adapter 使用(多数据类型)
		button_4.setOnClickListener {
			startActivity(Intent(this, MainActivity5::class.java))
		}

		//带刷新RecyclerView 使用
		button_5.setOnClickListener {
			startActivity(Intent(this, MainActivity6::class.java))
		}
	}
}
