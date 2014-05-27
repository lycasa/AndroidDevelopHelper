package com.yunchao.androiddevelophelper.sourceviews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.yunchao.androiddevelophelper.R;

public class SourceViewAcitivity extends Activity implements OnClickListener{
	private Button analogclock;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sourceview_main);
		initview();
	}

	private void initview() {
		analogclock = (Button) findViewById(R.id.analogclock);
		analogclock.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.analogclock:
			Intent analogclockintent = new Intent(this, AnalogclockActivity.class);
			startActivity(analogclockintent);
			break;

		default:
			break;
		}
		
	}
}
