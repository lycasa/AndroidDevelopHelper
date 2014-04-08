package com.yunchao.androiddevelophelper.views;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.baidu.frontia.Frontia;
import com.yunchao.androiddevelophelper.R;
import com.yunchao.androiddevelophelper.adapters.Main_ListViewAdapter;
import com.yunchao.androiddevelophelper.adapters.ViewPagerAdapter;
import com.yunchao.androiddevelophelper.global.Conf;

public class MainActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	private ListView lv_androidutils_text;
	private Button btn_androidutils, btn_2, btn_3, btn_moreinfo;
	private List<String> mdata = new ArrayList<String>();
	private Context mContext;
	private Main_ListViewAdapter mListViewAdapter;
	private LinearLayout middle_content_container;
	private LayoutInflater mlayoutinflater;
	private PluginScrollView mPluginScrollView;
	private ViewPager mViewPager;
	private ViewPagerAdapter viewPagerAdapter;
	List<View> testList;
	private View android_utils_main, button2layout, button3layout, login_main;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main_layout);
		setContentView(R.layout.scroll_main_layout);
		Frontia.init(this.getApplicationContext(), Conf.APIKEY);
		mContext = this;

		mlayoutinflater = getLayoutInflater();
		android_utils_main = mlayoutinflater.inflate(
				R.layout.android_utils_main, null);
		initData();
		initview();
		button2layout = mlayoutinflater.inflate(R.layout.button2layout, null);
		button3layout = mlayoutinflater.inflate(R.layout.button3layout, null);
		login_main = mlayoutinflater.inflate(R.layout.login_main, null);
		preInit();
		initView();

	}

	public void initView() {
		mViewPager = (ViewPager) findViewById(R.id.viewpagerLayout);
		viewPagerAdapter = new ViewPagerAdapter();
		viewPagerAdapter.setList(testList);
		mViewPager.setAdapter(viewPagerAdapter);
		mViewPager.setCurrentItem(0);
		// mPluginScrollView = new PluginScrollView(this, viewPager, testList);
		mPluginScrollView = (PluginScrollView) findViewById(R.id.horizontalScrollView);
		mPluginScrollView.setTestList(testList);
		mPluginScrollView.setViewPager(mViewPager);
		postInit();

	}

	private void preInit() {
		testList = new ArrayList<View>();
		testList.add(android_utils_main);
		testList.add(button2layout);
		testList.add(button3layout);
		testList.add(button2layout);
		testList.add(button3layout);
		testList.add(button2layout);
		testList.add(button3layout);
		testList.add(button2layout);
		testList.add(login_main);
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

	private void initview() {
		lv_androidutils_text = (ListView) android_utils_main
				.findViewById(R.id.lv_androidutils_text);
		mListViewAdapter = new Main_ListViewAdapter(mdata, mContext);
		lv_androidutils_text.setAdapter(mListViewAdapter);
		lv_androidutils_text.setOnItemClickListener(this);
		// btn_2=(Button) findViewById(R.id.btn_2);
		// btn_3=(Button) findViewById(R.id.btn_3);
		// btn_moreinfo=(Button) findViewById(R.id.btn_moreinfo);
		// btn_androidutils=(Button) findViewById(R.id.btn_androidutils);
	}

	private void initEvent() {
		btn_2.setOnClickListener(this);
		btn_3.setOnClickListener(this);
		btn_moreinfo.setOnClickListener(this);
		btn_androidutils.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_androidutils:

			middle_content_container.removeAllViews();
			View android_utils_main = mlayoutinflater.inflate(
					R.layout.android_utils_main, null);
			middle_content_container.addView(android_utils_main);
			initData();
			initview();
			break;
		case R.id.btn_2:

			middle_content_container.removeAllViews();
			View button2layout = mlayoutinflater.inflate(
					R.layout.button2layout, null);
			middle_content_container.addView(button2layout);
			break;
		case R.id.btn_3:

			middle_content_container.removeAllViews();
			View button3layout = mlayoutinflater.inflate(
					R.layout.button3layout, null);
			middle_content_container.addView(button3layout);
			break;
		case R.id.btn_moreinfo:
			middle_content_container.removeAllViews();
			View login_main = mlayoutinflater
					.inflate(R.layout.login_main, null);
			middle_content_container.addView(login_main);
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String str = lv_androidutils_text.getAdapter().getItem(position)
				.toString();
		Intent mintent = new Intent(this, ListView_Show_Activity.class);
		mintent.putExtra("str", str);
		startActivity(mintent);

	}

	private void postInit() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Log.d("k", "onPageSelected - " + arg0);
				mPluginScrollView.buttonSelected(arg0);
				mViewPager.setCurrentItem(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				Log.d("k", "onPageScrolled - " + arg0);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				Log.d("k", "onPageScrollStateChanged - " + arg0);
				// 状态有三个0空闲，1是增在滑行中，2目标加载完毕
			}
		});

	}
}
