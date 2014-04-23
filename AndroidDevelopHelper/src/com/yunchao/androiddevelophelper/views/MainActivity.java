package com.yunchao.androiddevelophelper.views;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.Toast;

import com.baidu.frontia.Frontia;
import com.yunchao.androiddevelophelper.R;
import com.yunchao.androiddevelophelper.adapters.Main_ListViewAdapter;
import com.yunchao.androiddevelophelper.adapters.ViewPagerAdapter;
import com.yunchao.androiddevelophelper.global.Conf;
import com.yunchao.androiddevelophelper.local.ReadFileAsyncTask;

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
		registerReadFileEndBroadcastReceiver();
		mlayoutinflater = getLayoutInflater();
		android_utils_main = mlayoutinflater.inflate(
				R.layout.android_utils_main, null);
	/*	ReadFileAsyncTask task = new ReadFileAsyncTask(MainActivity.this);
		task.execute();*/
		mdata=getFileData();
		//initData();
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
	
/**
 * 假数据
 */
	private void initData() {
		for(int i=0;i<20;i++){
			mdata.add(i+"");
		}
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
		switch (position) {
		case 0:
			Intent mintent = new Intent(this, ListView_Show_Activity.class);
			mintent.putExtra("str", str);
			startActivity(mintent);
			break;

		default:
			Intent nintent = new Intent(this, Other_Activity_Show.class);
			nintent.putExtra("str", str);
			startActivity(nintent);
			break;
		}
		
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
	//文件读取完发的广播
	public void sendReadFileEndBroadcast(List data){
	    Intent nintent=new Intent();
	    nintent.setAction("pasting_usb_remove");
	    Bundle mdata = new Bundle();
	    mdata.putCharSequenceArrayList("mbudle", (ArrayList<CharSequence>) data);
	    nintent.putExtra("data", mdata);
	    sendBroadcast(nintent);
	}
	//注册广播
	public void registerReadFileEndBroadcastReceiver(){
	    IntentFilter myIntentFilter = new IntentFilter();
	    myIntentFilter.addAction("pasting_usb_remove");
	    registerReceiver(rBroadcastReceiver, myIntentFilter);
	}
	//定义广播接受者
	private BroadcastReceiver rBroadcastReceiver=new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        // TODO Auto-generated method stub
	        String action=intent.getAction();
	        if(action.equals("pasting_usb_remove")){
	            //Toast.makeText(FileManagerActivity.this,getString(R.string.pasting_file_removed),0).show();
	        }
	        
	    }
	};
	protected void onDestroy() {
		unregisterReceiver(rBroadcastReceiver);
	};
	
	public List<String> getFileData(){
		ArrayList<String> info = null;
		File file=new File(Conf.DATA_FILE_PATH);
		if (file.exists() && file.isFile()) {
			try {
				String buffer = "";
				info = new ArrayList<String>();
				BufferedReader reader = new BufferedReader(new FileReader(file));
				while ((buffer = reader.readLine()) != null) {
					info.add(buffer);
				}
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return info;
	}
}
