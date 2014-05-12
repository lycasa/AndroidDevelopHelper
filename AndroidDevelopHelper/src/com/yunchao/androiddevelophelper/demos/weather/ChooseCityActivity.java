package com.yunchao.androiddevelophelper.demos.weather;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
	
	/**���������б�*/
	private void spinner() {
		// ��ʼ�������б�
		// ����������
		/*
		 * ArrayAdapter<CharSequence> createFromResource(Context context, int
		 * textArrayResId, int textViewResId)
		 *  ����: 
		 *  context�������б�������ڵĽ������
		 * textArrayResId����res/values/arrays.xml�����һ����� textViewResId�������б����ʾ���
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
				if(position != 0){//ѡ����ʡ��
					/*����һ��С����
					*0x7f050000ΪR�ļ���ʡ�������Ӧ��idֵ��ֻҪ����position�����ɻ�ö�Ӧѡ�ʡ�ݣ��ĳ���
					*�������������������ܾ�Ҫ��һ��ѵ��ж��������������
					*/
					int cityID = 0x7f050000 + position;
					
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
		if (v.getId() == R.id.saveBn) {// ����ѡ����У����ҷ�����һ���棬������ѡ��ĳ���
			if(citySpinner.getSelectedItem()!=null){
				String city = citySpinner.getSelectedItem().toString();
				SQLiteDao sqliteDao = new SQLiteDao(this);
				sqliteDao.insert(city);
				
				ChooseCityActivity.this.finish(); 
			}else{//���û��ѡ�����
				Toast.makeText(getApplicationContext(), R.string.choose_city, 1000).show();
			}
		} else if (v.getId() == R.id.cancelBn) {// ������һ���棬�൱�ڵ�����ؼ�
			ChooseCityActivity.this.finish(); 
		}
	}

}
