package com.yunchao.androiddevelophelper.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.yunchao.androiddevelophelper.R;

public class Other_Activity_Show extends Activity {
	private TextView textView1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other_activity_show);
		initdata();
	}

	private void initdata() {
		
		textView1=(TextView) findViewById(R.id.textView1);
		Intent mintent =getIntent();
		String str=mintent.getStringExtra("str");
		textView1.setText("这个是第"+str+"个Activity");
	}
}
