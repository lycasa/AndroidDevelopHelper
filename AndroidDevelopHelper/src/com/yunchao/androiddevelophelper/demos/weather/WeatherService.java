package com.yunchao.androiddevelophelper.demos.weather;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WeatherService extends Service {
	/**�˴�Ҫ��MainActivityһ��*/
	private final String BROADCAST_ACTION = "vaint.wyt.broadcast";
	private String city;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	/*
	 * ÿ�ε���startService����ִ��onStart
	 */
	@Override
	public void onStart(final Intent intent, int startId) {
		//����service����������ռ�����̣߳�����������뿪��һ���߳���ִ�к�ʱ����
		new Thread(new Runnable() {
			@Override
			public void run() {
				city = intent.getStringExtra("city");
				String[] weather = null;
				try {
					weather = getWeather();
				} catch (Exception e) {
					//����ʱ,����ֵ�����null������Ϣ
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
