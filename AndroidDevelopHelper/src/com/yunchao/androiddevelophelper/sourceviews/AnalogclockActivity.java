package com.yunchao.androiddevelophelper.sourceviews;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract.QuickContact;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AnalogClock;
import android.widget.DigitalClock;
import android.widget.QuickContactBadge;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
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
    private SeekBar sb_show;
    private RatingBar rb_show;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sourecview_analogclock);
		initview();
		 QuickContactBadge mPhotoView;    
         mPhotoView = (QuickContactBadge) findViewById(R.id.badge);    
         mPhotoView.assignContactFromPhone("18616761691", true);   
         mPhotoView.setMode(QuickContact.MODE_LARGE); 

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
		
		
		sb_show = (SeekBar) findViewById(R.id.sb_show);
		sb_show.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				Toast.makeText(AnalogclockActivity.this, String.valueOf(seekBar.getProgress())+"%", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				//Toast.makeText(AnalogclockActivity.this, String.valueOf(progress)+"%", Toast.LENGTH_SHORT).show();
				//Log.d("liyunchao", "==progress=="+sb_show.getProgress());
				
			}
		});
		rb_show = (RatingBar) findViewById(R.id.rb_show);
		rb_show.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				Toast.makeText(AnalogclockActivity.this, String.valueOf(rating), Toast.LENGTH_SHORT).show();
				
			}
		});
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
