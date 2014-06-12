package com.yunchao.androiddevelophelper.animation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.yunchao.androiddevelophelper.R;

public class Animation_MainActivity extends Activity {
	private ImageView iv_animation_show;
	private Button btn_animation_start;
	private AnimationDrawable mAnimationDrawable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animation_main);
		initview();
	}
	private void initview() {
		iv_animation_show =  (ImageView) findViewById(R.id.iv_animation_show);
		btn_animation_start = (Button) findViewById(R.id.btn_animation_start);
		btn_animation_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				iv_animation_show.setImageResource(R.drawable.animation1);
				
				mAnimationDrawable = (AnimationDrawable) iv_animation_show.getDrawable();
				
				mAnimationDrawable.start();
			}
		});
		
	}
}
