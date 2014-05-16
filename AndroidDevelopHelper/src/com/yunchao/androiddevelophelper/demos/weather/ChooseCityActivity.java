package com.yunchao.androiddevelophelper.demos.weather;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.yunchao.androiddevelophelper.R;

public class ChooseCityActivity extends Activity implements OnClickListener {
	private Spinner provinceSpinner;
	private Spinner citySpinner;
	private Button saveBn;
	private Button cancelBn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_city_setup);

		findView();
		spinner();
	}

	private void findView() {
		provinceSpinner = (Spinner) findViewById(R.id.provinceSpinner);
		citySpinner = (Spinner) findViewById(R.id.citySpinner);
		saveBn = (Button) findViewById(R.id.saveBn);
		cancelBn = (Button) findViewById(R.id.cancelBn);
		
		saveBn.setOnClickListener(this);
		cancelBn.setOnClickListener(this);
	}
	
	/**级联下拉列表*/
	private void spinner() {
		// 初始化下拉列表
		// 创建适配器
		/*
		 * ArrayAdapter<CharSequence> createFromResource(Context context, int
		 * textArrayResId, int textViewResId)
		 *  参数: 
		 *  context：下拉列表对象所在的界面对象
		 * textArrayResId：在res/values/arrays.xml定义的一组数据 textViewResId：下拉列表的显示风格
		 * 1)android.R.layout.simple_spinner_dropdown_item
		 * 2)android.R.layout.simple_spinner_item
		 */
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.provinces,android.R.layout.simple_spinner_item);
		provinceSpinner.setAdapter(adapter);
		
		provinceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(position != 0){//选择了省份
					/*这是一个小技巧
					*0x7f050000为R文件中省份数组对应的id值，只要加上position，即可获得对应选项（省份）的城市
					*如果不是用这个方法，可能就要用一大堆的判断语句来级联城市
					*/
					//int cityID = 0x7f050000 + position;
					int cityID = 0x7f070000 + position;
					ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), cityID, android.R.layout.simple_spinner_item);
					citySpinner.setAdapter(adapter);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.saveBn) {// 保存选择城市，并且返回上一界面，并发送选择的城市
			if(citySpinner.getSelectedItem()!=null){
				String city = citySpinner.getSelectedItem().toString();
				SQLiteDao sqliteDao = new SQLiteDao(this);
				sqliteDao.insert(city);
				
				ChooseCityActivity.this.finish(); 
			}else{//如果没有选择城市
				Toast.makeText(getApplicationContext(), R.string.choose_city, 1000).show();
			}
		} else if (v.getId() == R.id.cancelBn) {// 返回上一界面，相当于点击返回键
			ChooseCityActivity.this.finish(); 
		}
	}

}
