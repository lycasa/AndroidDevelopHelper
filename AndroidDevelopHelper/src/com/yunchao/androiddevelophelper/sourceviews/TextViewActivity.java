package com.yunchao.androiddevelophelper.sourceviews;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.yunchao.androiddevelophelper.R;

public class TextViewActivity extends Activity {
	private CompoundButton m;
	private CheckBox cb_show;
	private RadioGroup rg;
	private Switch switch_show;
	private ToggleButton tg_show;
	private CheckedTextView ctv_show1,ctv_show2;
	private Chronometer chronometer;
	private AutoCompleteTextView actv_content;
	private Button btn_search;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sourceview_textview);
		initview();
		initevent();
	}
	private void initevent() {
		cb_show.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(TextViewActivity.this, isChecked?"选中！":"取消选中！", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		switch_show.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(TextViewActivity.this, isChecked?"开！":"关！", Toast.LENGTH_SHORT).show();
				
			}
		});
		tg_show.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(TextViewActivity.this, isChecked?"打开！":"关闭！", Toast.LENGTH_SHORT).show();
				
			}
		});
		ctv_show1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ctv_show1.toggle();
				
			}
		});
		ctv_show2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ctv_show2.toggle();
				
			}
		});
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 这里可以设定：当搜索成功时，才执行保存操作
				saveHistory("history",actv_content);
				
			}
		});
	}
	private void initview() {
		cb_show = (CheckBox) findViewById(R.id.cb_show);
		switch_show = (Switch) findViewById(R.id.switch_show);
		tg_show = (ToggleButton) findViewById(R.id.tg_show);
		ctv_show1 = (CheckedTextView) findViewById(R.id.ctv_show1);
		ctv_show2 = (CheckedTextView) findViewById(R.id.ctv_show2);
		chronometer = (Chronometer) findViewById(R.id.chronometer);
		actv_content = (AutoCompleteTextView) findViewById(R.id.actv_content);
		initAutoComplete("history",actv_content);
		
		btn_search = (Button) findViewById(R.id.btn_search);
	}
	
	public void onstart(View view){
		chronometer.start();
	}
	public void onstop(View view){
		chronometer.stop();
	}
	public void onreset(View view){
		chronometer.setBase(SystemClock.elapsedRealtime());
	}

	/**
	 * 初始化AutoCompleteTextView，最多显示5项提示，使
	 * AutoCompleteTextView在一开始获得焦点时自动提示
	 * @param field 保存在sharedPreference中的字段名
	 * @param auto 要操作的AutoCompleteTextView
	 */
	private void initAutoComplete(String field,AutoCompleteTextView auto) {
		SharedPreferences sp = getSharedPreferences("network_url", 0);
		String longhistory = sp.getString("history", "nothing");
		String[]  hisArrays = longhistory.split(",");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, hisArrays);
		//只保留最近的50条的记录
		if(hisArrays.length > 50){
			String[] newArrays = new String[50];
			System.arraycopy(hisArrays, 0, newArrays, 0, 50);
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line, newArrays);
		}
		auto.setAdapter(adapter);
		auto.setDropDownHeight(350);
		auto.setThreshold(1);
		auto.setCompletionHint("最近的5条记录");
		auto.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				AutoCompleteTextView view = (AutoCompleteTextView) v;
				if (hasFocus) {
						view.showDropDown();
				}
			}
		});
	}



	/**
	 * 把指定AutoCompleteTextView中内容保存到sharedPreference中指定的字符段
	 * @param field  保存在sharedPreference中的字段名
	 * @param auto  要操作的AutoCompleteTextView
	 */
	private void saveHistory(String field,AutoCompleteTextView auto) {
		String text = auto.getText().toString();
		SharedPreferences sp = getSharedPreferences("network_url", 0);
		String longhistory = sp.getString(field, "nothing");
		if (!longhistory.contains(text + ",")) {
			StringBuilder sb = new StringBuilder(longhistory);
			sb.insert(0, text + ",");
			sp.edit().putString("history", sb.toString()).commit();
		}
}
}
