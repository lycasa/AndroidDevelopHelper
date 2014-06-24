package com.yunchao.androiddevelophelper.sourceviews;

import android.app.Activity;
import android.inputmethodservice.ExtractEditText;
import android.os.Bundle;
import android.view.TextureView;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.yunchao.androiddevelophelper.R;

public class ViewGroupActivity extends Activity {
	private CompoundButton m;
	private CheckBox cb_show;
	private RadioGroup rg;
	private Switch switch_show;
	private ToggleButton tg_show;
	private CheckedTextView ctv_show1,ctv_show2;
	private Chronometer chronometer;
	private AutoCompleteTextView actv_content;
	private Button btn_search;
	private ExtractEditText mv;
	private TextureView nn;
	private ViewStub x;
	private ViewGroup vg;
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sourceview_textview);
		initview();
		initevent();
		 
	}
	private void initevent() {
		
	}
	private void initview() {
		cb_show = (CheckBox) findViewById(R.id.cb_show);
		switch_show = (Switch) findViewById(R.id.switch_show);
		tg_show = (ToggleButton) findViewById(R.id.tg_show);
		ctv_show1 = (CheckedTextView) findViewById(R.id.ctv_show1);
		ctv_show2 = (CheckedTextView) findViewById(R.id.ctv_show2);
		chronometer = (Chronometer) findViewById(R.id.chronometer);
		actv_content = (AutoCompleteTextView) findViewById(R.id.actv_content);
		
		
		btn_search = (Button) findViewById(R.id.btn_search);
	}
	




 

  

}
