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

public class MainActivity extends Activity implements OnClickListener {
	private Button searchBn;
	private TextView weatherTV;
	private TextView currentCityTV;
	private ImageView startImage;
	private ImageView endImage;
	
	/** ����ͼ������</BR>ͼ��λ��drawable�У����ڱ��ؼ��ء� */
	private final int imagesCount = 32;
	/** ����ͼ��ID</BR>R�ļ��ж�Ӧ��IDֵ */
	private int[] imagesID = new int[imagesCount];
	
	/**���չ㲥������</BR>���ڽ���Service���ص�������Ϣ </BR>�˴�Ҫ��Serviceһ��*/
	private final String BROADCAST_ACTION = "vaint.wyt.broadcast";
	private MyBroadcastRecever myBroadcastRecever;
	
	/**���ý����Preferences</BR>���ڻ�ȡ���ý����ѡ����Ϣ*/
	private SharedPreferences preferences;
	
	//����������־�������ǣ���onResume������ѡ����Ҫ���õķ�����
	/**�Ƿ����õı�־*/
	private boolean isSetup = false;
	/**�Ƿ�ѡ����еı�־*/
	private boolean isChooseCity = false;
	
	/**��ȡ���õ�ѡ��*/
	private Map<String,Boolean> setup;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_main);
		
		loadView();

		getImagesID();
		
		setChooseCity();
		//���ȫ�����Preference
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		getSetupInfo();
		
		//ע��㲥������
		IntentFilter filter = new IntentFilter();
		myBroadcastRecever = new MyBroadcastRecever();
		//���ý��չ㲥�����ͣ�����Ҫ��Service�����õ�����ƥ�䣬��������AndroidManifest.xml�ļ���ע�� 
		filter.addAction(BROADCAST_ACTION);
		registerReceiver(myBroadcastRecever, filter);
		
	}
	
	/*
	 *ѡ�����֮�󣬷��ص�������ʱ�����µ�ǰ����
	 *����������ɺ�ͬ���������
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if(isChooseCity){//��ѡ����н��淵�ض�ִ��onResume
			setChooseCity();
		}
		if(isSetup){//�����ý��淵�ض�ִ��onResume
			getSetupInfo();
		}
	}
	
	@Override
	protected void onDestroy() {
		//ֹͣservice
		stopService(new Intent("WeatherService"));
		//���BroadcastReceiver�İ�״̬
		unregisterReceiver(myBroadcastRecever);
		super.onDestroy();
	}
	
	/**�������ͼ��ID</BR>R�ļ��ж�Ӧ��ֵ*/
	private void getImagesID() {
		Resources res = getResources();
		String imageName = "";
		for (int i = 0; i < imagesCount; i++) {
			//getIdentifier��������ʵ��ͨ��ͼƬ��ƻ������ԴID
			imageName = "a_" + i;// ���ﲻ��+".gif"�Ⱥ�׺�����Բ鿴R�ļ��е�drawable�࣬��������������û��.gif��׺
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
	
	/**�������ѡ���ֵ */
	private void getSetupInfo(){
		setup = new HashMap<String,Boolean>();
		setup.put("threeDay",preferences.getBoolean("threeDay", true));
		setup.put("cityInfo", preferences.getBoolean("cityInfo", true));
	}
	
	/** �����ݿ����������õ�ǰ���У�Ĭ��Ϊ���� */
	private void setChooseCity() {
		SQLiteDao sqliteDao = new SQLiteDao(this);
		currentCityTV.setText(sqliteDao.getCity());
	}

	// ����ť����¼�
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.currentCity) {// ��ѡ����н���
			isChooseCity = true;//ѡ�����
			isSetup = false;//��������
			Intent intent = new Intent(MainActivity.this,
					ChooseCityActivity.class);
			this.startActivity(intent);
		} else if (v.getId() == R.id.searchBn) {// ��ȡ������Ϣ
			String city = currentCityTV.getText().toString();

			//����Service���񣬵��ٴ�����ʱ������ִ��onCreate�����Ծ�ִ��onStart����
			Intent intent =new Intent("WeatherService");
			 intent.putExtra("city", city);
			this.startService(intent);
		}
	}

	//����һ���㲥�����������ڽ���Service��õ�������Ϣ
	class MyBroadcastRecever extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String[] weatherInfo = intent.getStringArrayExtra("weather");
			if(weatherInfo==null){
				Toast.makeText(MainActivity.this, "û�е�ǰ���е�������Ϣ", 1000).show();
			}else if(weatherInfo.length==1){//��weatherInfo = new String[]{"timeOut"};
				Toast.makeText(MainActivity.this, "���ӳ�ʱ����������", 1000).show();
			}else{
				showWeather(weatherInfo);
			}
		}
	}
	
	/**�������ѡ����ʾ������Ϣ*/
	private void showWeather(String[] weatherInfo) {
		// ��ݻ����ݣ�ѡ���Ӧ����ͼ��
		// String[8]Ϊ�������ƿ�ʼͼƬ��ƣ�String[9]Ϊ�������ƽ���ͼƬ��ơ���ʽΪ��3.gif
		String index = weatherInfo[8].split("\\.")[0];
		int startImageID = Integer.parseInt(index);
		startImage.setImageDrawable(getResources().getDrawable(
				imagesID[startImageID]));
		index = weatherInfo[9].split("\\.")[0];
		int endImageID = Integer.parseInt(index);
		endImage.setImageDrawable(getResources().getDrawable(
				imagesID[endImageID]));
		
		// ����Ϊ���շ�����ݽ���ɸѡ��һһ��Ӧ����
		StringBuilder sb = new StringBuilder();
		
		sb.append("��ѯʱ��\t" + weatherInfo[4] + "\r\n\r\n");
		
		String[] temp = weatherInfo[10].split(";|��");// һ��Ҫʹ�����ġ�������
		sb.append(temp[0] + " " + temp[1] + " " + temp[2] + "\r\n\r\n");
		
		sb.append("����\t" + weatherInfo[5] + " " + weatherInfo[6].split(" ")[1]
				+ " " + weatherInfo[7] + "\r\n\r\n");
		
		if(setup.get("threeDay")){//����û�ѡ��鿴���������
			sb.append("����\t" + weatherInfo[13] + " " + weatherInfo[12] + " "
					+ weatherInfo[14] + "\r\n\r\n");
			sb.append("����\t" + weatherInfo[18] + " " + weatherInfo[17] + " "
					+ weatherInfo[19] + "\r\n\r\n");
		}
		if(setup.get("cityInfo")){//����û�ѡ��鿴���н���
			sb.append("���н���" + "\r\n   " + weatherInfo[22].replaceAll("��", "��\r\n   ") + "\r\n");
		}
		weatherTV.setText(sb.toString());
	}

	// �����Զ���Menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, R.string.setupMenu);
		menu.add(0, 2, 2, R.string.exitMenu);
		// ���ò˵��ļ����¼�
		menu.getItem(0).setOnMenuItemClickListener(
				new OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						isSetup = true;//��������
						isChooseCity = false;//����ѡ�����
						Intent intent = new Intent(MainActivity.this,
								SetupActivity.class);
						startActivity(intent);
						return false;
					}
				});
		// �˳��˵��ļ����¼�
		menu.getItem(1).setOnMenuItemClickListener(
				new OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						// �Ƿ��˳�����Ի���
						new AlertDialog.Builder(MainActivity.this)
								.setTitle(R.string.notice)
								.setMessage(R.string.r_u_exit)
								.setPositiveButton(R.string.yes,
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// �˳�����
												MainActivity.this.finish();
											}
										}).setNegativeButton(R.string.no, null)
								.show();// ע�⣺һ��Ҫshow��
						return false;
					}
				});

		return super.onCreateOptionsMenu(menu);
	}

}