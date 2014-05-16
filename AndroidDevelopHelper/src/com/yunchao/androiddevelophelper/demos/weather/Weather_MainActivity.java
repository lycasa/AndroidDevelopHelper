package com.yunchao.androiddevelophelper.demos.weather;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yunchao.androiddevelophelper.R;

public class Weather_MainActivity extends Activity implements OnClickListener {
	private Button searchBn;
	private TextView weatherTV;
	private TextView currentCityTV;
	private ImageView startImage;
	private ImageView endImage;
	
	/** 天气图标数量</BR>图标位于drawable中，属于本地加载。 */
	private final int imagesCount = 32;
	/** 天气图标ID</BR>R文件中对应的ID值 */
	private int[] imagesID = new int[imagesCount];
	
	/**接收广播的类型</BR>用于接收Service返回的天气信息 </BR>此处要与Service一致*/
	private final String BROADCAST_ACTION = "vaint.wyt.broadcast";
	private MyBroadcastRecever myBroadcastRecever;
	
	/**设置界面的Preferences</BR>用于获取设置界面的选项信息*/
	private SharedPreferences preferences;
	
	//下面两个标志的作用是：在onResume方法中选择需要调用的方法。
	/**是否设置的标志*/
	private boolean isSetup = false;
	/**是否选择城市的标志*/
	private boolean isChooseCity = false;
	
	/**获取设置的选项*/
	private Map<String,Boolean> setup;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_main);
		
		loadView();

		getImagesID();
		
		setChooseCity();
		//获得全局域的Preference
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		getSetupInfo();
		
		//注册广播接收器
		IntentFilter filter = new IntentFilter();
		myBroadcastRecever = new MyBroadcastRecever();
		//设置接收广播的类型，这里要和Service里设置的类型匹配，还可以在AndroidManifest.xml文件中注册 
		filter.addAction(BROADCAST_ACTION);
		registerReceiver(myBroadcastRecever, filter);
		
	}
	
	/*
	 *选择城市之后，返回到主界面时，更新当前城市
	 *或者设置完成后，同步处理数据
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if(isChooseCity){//由选择城市界面返回而执行onResume
			setChooseCity();
		}
		if(isSetup){//由设置界面返回而执行onResume
			getSetupInfo();
		}
	}
	
	@Override
	protected void onDestroy() {
		//停止service
		stopService(new Intent("WeatherService"));
		//解除BroadcastReceiver的绑定状态
		unregisterReceiver(myBroadcastRecever);
		super.onDestroy();
	}
	
	/**获得天气图标ID</BR>R文件中对应的值*/
	private void getImagesID() {
		Resources res = getResources();
		String imageName = "";
		for (int i = 0; i < imagesCount; i++) {
			//getIdentifier方法可以实现通过图片名称获得其资源ID
			imageName = "a_" + i;// 这里不能+".gif"等后缀。可以查看R文件中的drawable类，里面的名属性名均没有.gif后缀
			imagesID[i] = res.getIdentifier(imageName, "drawable",getPackageName());
		}
	}

	private void loadView() {
		searchBn = (Button) findViewById(R.id.searchBn);
		currentCityTV = (TextView) findViewById(R.id.currentCity);
		weatherTV = (TextView) findViewById(R.id.weatherTV);
		startImage = (ImageView) findViewById(R.id.startGraph);
		endImage = (ImageView) findViewById(R.id.endGraph);

		currentCityTV.setOnClickListener(this);
		searchBn.setOnClickListener(this);
	}
	
	/**获得设置选项的值 */
	private void getSetupInfo(){
		setup = new HashMap<String,Boolean>();
		setup.put("threeDay",preferences.getBoolean("threeDay", true));
		setup.put("cityInfo", preferences.getBoolean("cityInfo", true));
	}
	
	/** 根据数据库的数据来设置当前城市，默认为广州 */
	private void setChooseCity() {
		SQLiteDao sqliteDao = new SQLiteDao(this);
		currentCityTV.setText(sqliteDao.getCity());
	}

	// 监听按钮点击事件
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.currentCity) {// 打开选择城市界面
			isChooseCity = true;//选择城市
			isSetup = false;//并非设置
			Intent intent = new Intent(Weather_MainActivity.this,
					ChooseCityActivity.class);
			this.startActivity(intent);
		} else if (v.getId() == R.id.searchBn) {// 获取天气信息
			String city = currentCityTV.getText().toString();

			//开启Service服务，当再次启动时，不会执行onCreate，但仍旧执行onStart方法
			Intent intent =new Intent("WeatherService");
			 intent.putExtra("city", city);
			this.startService(intent);
		}
	}

	//定义一个广播接收器，用于接收Service获得的天气信息
	class MyBroadcastRecever extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String[] weatherInfo = intent.getStringArrayExtra("weather");
			if(weatherInfo==null){
				Toast.makeText(Weather_MainActivity.this, "没有当前城市的天气信息", 1000).show();
			}else if(weatherInfo.length==1){//即weatherInfo = new String[]{"timeOut"};
				Toast.makeText(Weather_MainActivity.this, "连接超时，请检查网络", 1000).show();
			}else{
				showWeather(weatherInfo);
			}
		}
	}
	
	/**根据设置选项显示天气信息*/
	private void showWeather(String[] weatherInfo) {
		// 根据获得数据，选择对应天气图标
		// String[8]为天气趋势开始图片名称，String[9]为天气趋势结束图片名称。格式为：3.gif
		String index = weatherInfo[8].split("\\.")[0];
		int startImageID = Integer.parseInt(index);
		startImage.setImageDrawable(getResources().getDrawable(
				imagesID[startImageID]));
		index = weatherInfo[9].split("\\.")[0];
		int endImageID = Integer.parseInt(index);
		endImage.setImageDrawable(getResources().getDrawable(
				imagesID[endImageID]));
		
		// 以下为参照返回数据进行筛选，一一对应即可
		StringBuilder sb = new StringBuilder();
		
		sb.append("查询时间\t" + weatherInfo[4] + "\r\n\r\n");
		
		String[] temp = weatherInfo[10].split(";|；");// 一般要使用中文“；”。
		sb.append(temp[0] + " " + temp[1] + " " + temp[2] + "\r\n\r\n");
		
		sb.append("今天\t" + weatherInfo[5] + " " + weatherInfo[6].split(" ")[1]
				+ " " + weatherInfo[7] + "\r\n\r\n");
		
		if(setup.get("threeDay")){//如果用户选择查看三天的天气
			sb.append("明天\t" + weatherInfo[13] + " " + weatherInfo[12] + " "
					+ weatherInfo[14] + "\r\n\r\n");
			sb.append("后天\t" + weatherInfo[18] + " " + weatherInfo[17] + " "
					+ weatherInfo[19] + "\r\n\r\n");
		}
		if(setup.get("cityInfo")){//如果用户选择查看城市介绍
			sb.append("城市介绍" + "\r\n   " + weatherInfo[22].replaceAll("。", "。\r\n   ") + "\r\n");
		}
		weatherTV.setText(sb.toString());
	}

	// 设置自定义Menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, R.string.setupMenu);
		menu.add(0, 2, 2, R.string.exitMenu);
		// 设置菜单的监听事件
		menu.getItem(0).setOnMenuItemClickListener(
				new OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						isSetup = true;//进入设置
						isChooseCity = false;//并非选择城市
						Intent intent = new Intent(Weather_MainActivity.this,
								SetupActivity.class);
						startActivity(intent);
						return false;
					}
				});
		// 退出菜单的监听事件
		menu.getItem(1).setOnMenuItemClickListener(
				new OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						// 是否退出程序对话框
						new AlertDialog.Builder(Weather_MainActivity.this)
								.setTitle(R.string.notice)
								.setMessage(R.string.r_u_exit)
								.setPositiveButton(R.string.yes,
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// 退出程序
												Weather_MainActivity.this.finish();
											}
										}).setNegativeButton(R.string.no, null)
								.show();// 注意：一定要show！
						return false;
					}
				});

		return super.onCreateOptionsMenu(menu);
	}

}