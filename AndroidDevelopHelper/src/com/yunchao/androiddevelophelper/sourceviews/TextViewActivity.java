package com.yunchao.androiddevelophelper.sourceviews;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.inputmethodservice.ExtractEditText;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.yunchao.androiddevelophelper.R;

public class TextViewActivity extends Activity {
	private CompoundButton m;
	private CheckBox cb_show;
	private RadioGroup rg;
	private Switch switch_show;
	private ToggleButton tg_show;
	private CheckedTextView ctv_show1,ctv_show2;
	private Chronometer chronometer;
	private AutoCompleteTextView actv_content;
	private Button btn_search;
	private ExtractEditText mv;
	private TextureView nn;
	private ViewStub x;
	private ViewGroup vg;
	
	 public static final int MEDIA_TYPE_IMAGE = 1;
	    public static final int MEDIA_TYPE_VIDEO = 2;
	    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	    private Camera mCamera;
	    private CameraPreview mPreview;
	    private static final String TAG = "ERROR";
	    private PictureCallback mPicture = new PictureCallback() {
	        @Override
	        public void onPictureTaken(byte[] data, Camera camera) {
	            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
	            if (pictureFile == null) {
	                Log.d(TAG,
	                        "Error creating media file, check storage permissions: "
	                                + "e.getMessage()");
	                return;
	            }
	            try {
	                FileOutputStream fos = new FileOutputStream(pictureFile);
	                fos.write(data);
	                fos.close();
	            } catch (FileNotFoundException e) {
	                Log.d(TAG, "File not found: " + e.getMessage());
	            } catch (IOException e) {
	                Log.d(TAG, "Error accessing file: " + e.getMessage());
	            }
	        }
	    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sourceview_textview);
		initview();
		initevent();
		 // 创建Camera实例
        mCamera = getCameraInstance();
        // 创建Preview view并将其设为activity中的内容
        mPreview = new CameraPreview(this, mCamera);
        mPreview.setSurfaceTextureListener(mPreview);
        //设置浑浊
        mPreview.setAlpha(0.5f);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        // preview.setAlpha(0.0f);
        preview.addView(mPreview);
        // 在Capture按钮中加入listener
        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 从摄像头获取图片
                mCamera.takePicture(null, null, mPicture);
            }
        });
	}
	private void initevent() {
		cb_show.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(TextViewActivity.this, isChecked?"选中！":"取消选中！", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		switch_show.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(TextViewActivity.this, isChecked?"开！":"关！", Toast.LENGTH_SHORT).show();
				
			}
		});
		tg_show.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(TextViewActivity.this, isChecked?"打开！":"关闭！", Toast.LENGTH_SHORT).show();
				
			}
		});
		ctv_show1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ctv_show1.toggle();
				
			}
		});
		ctv_show2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ctv_show2.toggle();
				
			}
		});
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 这里可以设定：当搜索成功时，才执行保存操作
				saveHistory("history",actv_content);
				
			}
		});
	}
	private void initview() {
		cb_show = (CheckBox) findViewById(R.id.cb_show);
		switch_show = (Switch) findViewById(R.id.switch_show);
		tg_show = (ToggleButton) findViewById(R.id.tg_show);
		ctv_show1 = (CheckedTextView) findViewById(R.id.ctv_show1);
		ctv_show2 = (CheckedTextView) findViewById(R.id.ctv_show2);
		chronometer = (Chronometer) findViewById(R.id.chronometer);
		actv_content = (AutoCompleteTextView) findViewById(R.id.actv_content);
		initAutoComplete("history",actv_content);
		
		btn_search = (Button) findViewById(R.id.btn_search);
	}
	
	public void onstart(View view){
		chronometer.start();
	}
	public void onstop(View view){
		chronometer.stop();
	}
	public void onreset(View view){
		chronometer.setBase(SystemClock.elapsedRealtime());
	}

	/**
	 * 初始化AutoCompleteTextView，最多显示5项提示，使
	 * AutoCompleteTextView在一开始获得焦点时自动提示
	 * @param field 保存在sharedPreference中的字段名
	 * @param auto 要操作的AutoCompleteTextView
	 */
	private void initAutoComplete(String field,AutoCompleteTextView auto) {
		SharedPreferences sp = getSharedPreferences("network_url", 0);
		String longhistory = sp.getString("history", "nothing");
		String[]  hisArrays = longhistory.split(",");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, hisArrays);
		//只保留最近的50条的记录
		if(hisArrays.length > 50){
			String[] newArrays = new String[50];
			System.arraycopy(hisArrays, 0, newArrays, 0, 50);
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line, newArrays);
		}
		auto.setAdapter(adapter);
		auto.setDropDownHeight(350);
		auto.setThreshold(1);
		auto.setCompletionHint("最近的5条记录");
		auto.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				AutoCompleteTextView view = (AutoCompleteTextView) v;
				if (hasFocus) {
						view.showDropDown();
				}
			}
		});
	}



	/**
	 * 把指定AutoCompleteTextView中内容保存到sharedPreference中指定的字符段
	 * @param field  保存在sharedPreference中的字段名
	 * @param auto  要操作的AutoCompleteTextView
	 */
	private void saveHistory(String field,AutoCompleteTextView auto) {
		String text = auto.getText().toString();
		SharedPreferences sp = getSharedPreferences("network_url", 0);
		String longhistory = sp.getString(field, "nothing");
		if (!longhistory.contains(text + ",")) {
			StringBuilder sb = new StringBuilder(longhistory);
			sb.insert(0, text + ",");
			sp.edit().putString("history", sb.toString()).commit();
		}
}
	
	
	 /** 安全获取Camera对象实例的方法 */

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // 试图获取Camera实例
        }
        catch (Exception e) {
            // 摄像头不可用（正被占用或不存在）
        }
        return c; // 不可用则返回null
    }

    
    /** 为保存图片或视频创建File */
    private static File getOutputMediaFile(int type) {
        // 安全起见，在使用前应该
        // 用Environment.getExternalStorageState()检查SD卡是否已装入
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        // 如果期望图片在应用程序卸载后还存在、且能被其它应用程序共享，
        // 则此保存位置最合适
        // 如果不存在的话，则创建存储目录
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
            Log.d("MyCameraApp", "failed to create directory");
        }
        // 创建媒体文件名
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // 捕获的图像保存到Intent指定的fileUri
                Toast.makeText(this, "Image saved to:\n" + data.getData(),
                        Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // 用户取消了图像捕获
            } else {
                // 图像捕获失败，提示用户
            }
        }

        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // 捕获的视频保存到Intent指定的fileUri
                Toast.makeText(this, "Video saved to:\n" + data.getData(),
                        Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // 用户取消了视频捕获
            } else {
                // 视频捕获失败，提示用户
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera(); // 在暂停事件中立即释放摄像头
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release(); // 为其它应用释放摄像头
            mCamera = null;
        }
    }

}
