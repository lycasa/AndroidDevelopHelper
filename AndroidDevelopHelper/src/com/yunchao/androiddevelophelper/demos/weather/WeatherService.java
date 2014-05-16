package com.yunchao.androiddevelophelper.demos.weather;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WeatherService extends Service {
	/**此处要与MainActivity一致*/
	private final String BROADCAST_ACTION = "vaint.wyt.broadcast";
	private String city;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	/*
	 * 每次调用startService，将执行onStart
	 */
	@Override
	public void onStart(final Intent intent, int startId) {
		//由于service开启后，依旧占用主线程，所以这里必须开启一个线程来执行耗时操作
		new Thread(new Runnable() {
			@Override
			public void run() {
				city = intent.getStringExtra("city");
				String[] weather = null;
				try {
					weather = getWeather();
				} catch (Exception e) {
					//发生超时,返回值区别于null与正常信息
					weather = new String[]{"timeOut"};
					e.printStackTrace();
				} 
				Intent i = new Intent();
				i.putExtra("weather", weather);
				i.setAction(BROADCAST_ACTION);
				sendBroadcast(i);
			}
		}).start();
		
		super.onStart(intent, startId);
	}

	private String[] getWeather() throws IOException, XmlPullParserException{
		return WeatherUtils.getWeather(city);
	}

}
