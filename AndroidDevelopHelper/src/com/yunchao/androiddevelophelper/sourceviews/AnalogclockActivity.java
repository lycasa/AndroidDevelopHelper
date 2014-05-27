package com.yunchao.androiddevelophelper.sourceviews;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AnalogClock;
import android.widget.DigitalClock;
import android.widget.TextClock;

import com.yunchao.androiddevelophelper.R;

public class AnalogclockActivity extends Activity {
	private AnalogClock analogclock;
    private DigitalClock digitalclock;
    private TextClock textclock;
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
	//对AnalogClock的操作待研究
	}
}
