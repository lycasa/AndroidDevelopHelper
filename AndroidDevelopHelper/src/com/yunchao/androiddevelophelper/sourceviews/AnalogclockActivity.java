package com.yunchao.androiddevelophelper.sourceviews;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AnalogClock;
import android.widget.DigitalClock;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButton;
import android.widget.ZoomControls;

import com.yunchao.androiddevelophelper.R;

public class AnalogclockActivity extends Activity implements OnClickListener{
	private AnalogClock analogclock;
    private DigitalClock digitalclock;
    private TextClock textclock;
    private ZoomButton zoombutton;
    static long size = 12;
    private TextView tv_help;
    private ZoomControls zoomcontrols;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sourecview_analogclock);
		initview();
	}

	@SuppressWarnings("deprecation")
	private void initview() {
		analogclock =  (AnalogClock) findViewById(R.id.analogclock);
		digitalclock = (DigitalClock) findViewById(R.id.digitalclock);
		textclock = (TextClock) findViewById(R.id.textclock);
		zoombutton = (ZoomButton) findViewById(R.id.zoombutton);
		zoombutton.setOnClickListener(this);
		//zoombutton.setZoomSpeed(3l);
		tv_help = (TextView) findViewById(R.id.tv_help);
		zoomcontrols = (ZoomControls) findViewById(R.id.zoomcontrols);
		zoomcontrols.setOnZoomInClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(AnalogclockActivity.this, "zoomin!", Toast.LENGTH_SHORT).show();
				
			}
		});
		zoomcontrols.setOnZoomOutClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(AnalogclockActivity.this, "zoomout!", Toast.LENGTH_SHORT).show();
				
			}
		});
	//对AnalogClock的操作待研究
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.zoombutton:
			Log.d("liyunchao", "==zoombuttonclick==");
			
			size = size + 2;
			tv_help.setTextSize(size);
			break;

		default:
			break;
		}
		
	}
}
