package com.yunchao.androiddevelophelper.views;

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

import com.baidu.frontia.Frontia;
import com.yunchao.androiddevelophelper.R;
import com.yunchao.androiddevelophelper.adapters.Main_ListViewAdapter;
import com.yunchao.androiddevelophelper.adapters.ViewPagerAdapter;
import com.yunchao.androiddevelophelper.customviews.AbPullListView;
import com.yunchao.androiddevelophelper.demos.light.LightActivity;
import com.yunchao.androiddevelophelper.demos.news.News_MainActivity;
import com.yunchao.androiddevelophelper.demos.shake.ShakeActivity;
import com.yunchao.androiddevelophelper.demos.tts.TTSDemoActivity;
import com.yunchao.androiddevelophelper.demos.weather.Weather_MainActivity;
import com.yunchao.androiddevelophelper.demos.weixin.IndexActivity;
import com.yunchao.androiddevelophelper.demos.zxing.ZXingMainActivity;
import com.yunchao.androiddevelophelper.global.Conf;
import com.yunchao.androiddevelophelper.listener.AbOnListViewListener;
import com.yunchao.androiddevelophelper.sourceviews.SourceViewAcitivity;
import com.yunchao.androiddevelophelper.utils.BaseUtils;

public class MainActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	private AbPullListView lv_androidutils_text;
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
	private View android_utils_main, gamelayout, button3layout, login_main;
	private final static String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main_layout);
		setContentView(R.layout.scroll_main_layout);
		Frontia.init(this.getApplicationContext(), Conf.APIKEY);
		mContext = this;
		registerReadFileEndBroadcastReceiver();
		mlayoutinflater = getLayoutInflater();
		initAppLayout();
		/*android_utils_main = mlayoutinflater.inflate(
				R.layout.apps_main, null);*/
	/*	ReadFileAsyncTask task = new ReadFileAsyncTask(MainActivity.this);
		task.execute();*/
		/*try {
			//mdata=getFileData(0);
			mdata=BaseUtils.getFileData(Conf.ASSETS_APPS_PATH, mContext);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//initData();
		//initview();
		//gamelayout = mlayoutinflater.inflate(R.layout.games_main, null);
		gamelayout = new GamesMainView(mContext,mlayoutinflater);
		//initGameLayout();
		button3layout = mlayoutinflater.inflate(R.layout.button3layout, null);
		login_main = mlayoutinflater.inflate(R.layout.login_main, null);
		preInit();
		initView();

	}

	private void initAppLayout(){
		android_utils_main = mlayoutinflater.inflate(
				R.layout.apps_main, null);
		try {
			//mdata=getFileData(0);
			mdata=BaseUtils.getFileData(Conf.ASSETS_APPS_PATH, mContext);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initview(android_utils_main);
		
	}
	
	private void initGameLayout(){
		gamelayout = mlayoutinflater.inflate(R.layout.apps_main, null);
		try {
			//mdata=getFileData(0);
			mdata=BaseUtils.getFileData(Conf.ASSETS_GAMES_PATH, mContext);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initview(gamelayout);
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
		testList.add(gamelayout);
		testList.add(button3layout);
		testList.add(gamelayout);
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

	private void initview(View mview) {
		lv_androidutils_text = (AbPullListView) mview
				.findViewById(R.id.lv_androidutils_text);
		mListViewAdapter = new Main_ListViewAdapter(mdata, mContext);
		lv_androidutils_text.setAdapter(mListViewAdapter);
		lv_androidutils_text.setOnItemClickListener(this);
		// btn_2=(Button) findViewById(R.id.btn_2);
		// btn_3=(Button) findViewById(R.id.btn_3);
		// btn_moreinfo=(Button) findViewById(R.id.btn_moreinfo);
		// btn_androidutils=(Button) findViewById(R.id.btn_androidutils);
		//打开关闭下拉刷新加载更多功能
		lv_androidutils_text.setPullRefreshEnable(true); 
		lv_androidutils_text.setPullLoadEnable(true);
		  //设置进度条的样式
		lv_androidutils_text.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
		lv_androidutils_text.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
		lv_androidutils_text.setAbOnListViewListener(new AbOnListViewListener() {
			
			@Override
			public void onRefresh() {
				Log.d(TAG, "==onRefresh==");
				removeProgressDialog();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Toast.makeText(mContext, "刷新成功！", Toast.LENGTH_SHORT).show();
				lv_androidutils_text.stopRefresh();
				
			}
			
			@Override
			public void onLoadMore() {
				Log.d(TAG, "==onLoadMore==");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Toast.makeText(mContext, "加载更多成功！", Toast.LENGTH_SHORT).show();
				lv_androidutils_text.stopLoadMore();
			}
		});
		
		
		
		
	}

	private void initEvent() {
		btn_2.setOnClickListener(this);
		btn_3.setOnClickListener(this);
		btn_moreinfo.setOnClickListener(this);
		btn_androidutils.setOnClickListener(this);
	}
	/**
	 * 描述：移除进度框.
	 */
	public void removeProgressDialog() {
		removeDialog(0);
    }
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_androidutils:

			middle_content_container.removeAllViews();
			View android_utils_main = mlayoutinflater.inflate(
					R.layout.apps_main, null);
			middle_content_container.addView(android_utils_main);
			initData();
			initview(android_utils_main);
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
		case 1:
			Intent ttsintent = new Intent(this, TTSDemoActivity.class);
			ttsintent.putExtra("str", str);
			startActivity(ttsintent);
			break;
		case 2:
			Intent zxingintent = new Intent(this, ZXingMainActivity.class);
			zxingintent.putExtra("str", str);
			startActivity(zxingintent);
			break;
		case 3:
			Intent shakeintent = new Intent(this, ShakeActivity.class);
			shakeintent.putExtra("str", str);
			startActivity(shakeintent);
			break;
	/*	case 4:
			Intent game2048intent = new Intent(this, Game2048MainActivity.class);
			game2048intent.putExtra("str", str);
			startActivity(game2048intent);
			break;*/
		case 4:
			Intent lightintent = new Intent(this, LightActivity.class);
			lightintent.putExtra("str", str);
			startActivity(lightintent);
			break;
		case 5:
			Intent weixinintent = new Intent(this, IndexActivity.class);
			startActivity(weixinintent);
			break;
		case 6:
			Intent weibointent = new Intent(this, IndexActivity.class);
			startActivity(weibointent);
			break;
		case 7:
			Intent weatherintent = new Intent(this, Weather_MainActivity.class);
			startActivity(weatherintent);
			break;
		case 10:
			Intent newsintent = new Intent(this, News_MainActivity.class);
			startActivity(newsintent);
			break;
		case 11:
			Intent sourceviewintent = new Intent(this, SourceViewAcitivity.class);
			startActivity(sourceviewintent);
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
				Log.d("mkkkk", "onPageSelected - " + arg0);
				mPluginScrollView.buttonSelected(arg0);
				mViewPager.setCurrentItem(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				Log.d("mkkkk", "onPageScrolled - " + arg0);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				Log.d("mkkkk", "onPageScrollStateChanged - " + arg0);
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
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(rBroadcastReceiver);
	};
	
/*	public List<String> getFileData(int pagenum) throws IOException{
		ArrayList<String> info = null;
		InputStream assetsfilepath = null;
		switch (pagenum) {
		case 0:
			assetsfilepath = getClass().getResourceAsStream("/assets/appdatas");
			break;
		case 1:
			assetsfilepath = getClass().getResourceAsStream("/assets/gamedatas");
			break;

		default:
			break;
		}
		// InputStream abpath = getClass().getResourceAsStream("/assets/data");
		 String path = new String(InputStreamToByte(assetsfilepath));
		//File file=new File(Conf.DATA_FILE_PATH);
		File file=new File(path);
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
		String paths[] = path.split("\n");
		info = new ArrayList<String>();
		for(int i=0;i<paths.length;i++){
			info.add(paths[i]);
		}
		return info;
	}
    public byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }*/
}
