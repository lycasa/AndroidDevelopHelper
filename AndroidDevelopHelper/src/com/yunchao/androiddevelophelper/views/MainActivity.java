package com.yunchao.androiddevelophelper.views;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.baidu.frontia.Frontia;
import com.yunchao.androiddevelophelper.R;
import com.yunchao.androiddevelophelper.adapters.Main_ListViewAdapter;
import com.yunchao.androiddevelophelper.global.Conf;

public class MainActivity extends Activity implements OnClickListener{
	private ListView lv_androidutils_text;
	private RelativeLayout rl_main_listview_item;
	private Button btn_2,btn_3,btn_moreinfo;
	private List<String> mdata = new ArrayList<String>();
	private Context mContext;
	private Main_ListViewAdapter mListViewAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		Frontia.init(this.getApplicationContext(), Conf.APIKEY);
		mContext=this;
		//initview();
		//initEvent();
		//initData();
	}
	private void initData() {
		mdata.add("jhgkfkhgjfklhjfkhjfkljh");
		mdata.add("jhgkf84u694649khgjfklhjfkhjfkljh");
		mdata.add("jhgkfkhgjfklhjf464646khjfkljh");
		mdata.add("jhgk36464fkhgjfklhjfkhjfkljh");
		mdata.add("jhgkfkhgjfkl6466hjfkhjfkljh");
		mdata.add("jhgkfkhgjfkl3y6464hjfkhjfkljh");
		mdata.add("46446464646466456v45gv456gv56");
		mdata.add("jhgkfkhgjfklhjfkhjfkljh");
		mdata.add("jhgkf84u694649khgjfklhjfkhjfkljh");
		mdata.add("jhgkfkhgjfklhjf464646khjfkljh");
		mdata.add("jhgk36464fkhgjfklhjfkhjfkljh");
		mdata.add("jhgkfkhgjfkl6466hjfkhjfkljh");
		mdata.add("jhgkfkhgjfkl3y6464hjfkhjfkljh");
		mdata.add("46446464646466456v45gv456gv56");
		mdata.add("jhgkfkhgjfklhjfkhjfkljh");
		mdata.add("jhgkf84u694649khgjfklhjfkhjfkljh");
		mdata.add("jhgkfkhgjfklhjf464646khjfkljh");
		mdata.add("jhgk36464fkhgjfklhjfkhjfkljh");
		mdata.add("jhgkfkhgjfkl6466hjfkhjfkljh");
		mdata.add("jhgkfkhgjfkl3y6464hjfkhjfkljh");
		mdata.add("46446464646466456v45gv456gv56");
		mdata.add("jhgkfkhgjfklhjfkhjfkljh");
		mdata.add("jhgkf84u694649khgjfklhjfkhjfkljh");
		mdata.add("jhgkfkhgjfklhjf464646khjfkljh");
		mdata.add("jhgk36464fkhgjfklhjfkhjfkljh");
		mdata.add("jhgkfkhgjfkl6466hjfkhjfkljh");
		mdata.add("jhgkfkhgjfkl3y6464hjfkhjfkljh");
		mdata.add("46446464646466456v45gv456gv56");
		
	}
	private void initview(){
		lv_androidutils_text=(ListView) findViewById(R.id.lv_androidutils_text);
		mListViewAdapter = new Main_ListViewAdapter(mdata, mContext);
		lv_androidutils_text.setAdapter(mListViewAdapter);
		
		
		btn_2=(Button) findViewById(R.id.btn_2);
		btn_3=(Button) findViewById(R.id.btn_3);
		btn_moreinfo=(Button) findViewById(R.id.btn_moreinfo);
	}
	private void initEvent(){
		btn_2.setOnClickListener(this);
		btn_3.setOnClickListener(this);
		btn_moreinfo.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_moreinfo:
			Intent toQQ_login_main=new Intent(this, Login_mainActivity.class);
			startActivity(toQQ_login_main);
			break;

		default:
			break;
		}
	}
}
