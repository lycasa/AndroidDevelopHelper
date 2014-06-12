package com.yunchao.androiddevelophelper.sourceviews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.yunchao.androiddevelophelper.R;

public class SourceViewAcitivity extends Activity implements OnClickListener{
	private Button analogclock,btn_surfaceview,btn_glsurfaceview;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sourceview_main);
		initview();
	}

	private void initview() {
		analogclock = (Button) findViewById(R.id.analogclock);
		analogclock.setOnClickListener(this);
		btn_surfaceview = (Button) findViewById(R.id.btn_surfaceview);
		btn_surfaceview.setOnClickListener(this);
		btn_glsurfaceview = (Button) findViewById(R.id.btn_glsurfaceview);
		btn_glsurfaceview.setOnClickListener(this);
	
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.analogclock:
			Intent analogclockintent = new Intent(this, AnalogclockActivity.class);
			startActivity(analogclockintent);
			break;
		
		case R.id.btn_surfaceview:
			Intent surfaceviewintent = new Intent(this, SurfaceViewActivity.class);
			startActivity(surfaceviewintent);
			break;
			
		case R.id.btn_glsurfaceview:
			Intent glsurfaceviewintent = new Intent(this, GLSurfaceViewActivity.class);
			startActivity(glsurfaceviewintent);
			break;
			
		default:
			break;
		}
		
	}
	
}
