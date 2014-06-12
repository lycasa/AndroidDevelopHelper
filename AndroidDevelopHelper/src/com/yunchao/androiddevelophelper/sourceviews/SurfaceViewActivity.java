package com.yunchao.androiddevelophelper.sourceviews;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.yunchao.androiddevelophelper.R;

public class SurfaceViewActivity extends Activity implements OnClickListener {
	private Button button2,button3,button4,button5,button6;
	private SurfaceView sv_show;
	private SurfaceHolder mSurfaceHolder;
	private Timer mTimer;
	TextView m;
    private MyTimerTask mTimerTask;
    private int Y_axis[],//保存正弦波的Y轴上的点
	centerY,//中心线
	oldX,oldY,//上一个XY点 
	currentX;//当前绘制到的X轴上的点
    private GLSurfaceView glSurefaceView; 
    private final String TAG = "SurfaceViewActivity";  
    private VideoView vv_show;
    private  MediaController  mediaco;  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.sourceview_surfaceview);
		initview();
		/*glSurefaceView = new GLSurfaceView(this);  
        glSurefaceView.setRenderer(new GLSurfaceViewRender());  
        this.setContentView(glSurefaceView);*/
    }
    @Override  
    protected void onResume() {  
        super.onResume();  
        //glSurefaceView.onResume();  
    }  
  
    @Override  
    protected void onPause() {  
        super.onStop();  
       // glSurefaceView.onPause();  
    }  
  
	private void initview() {
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(this);
		button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(this);
		button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(this);
		button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(this);
		button6 = (Button) findViewById(R.id.button6);
		button6.setOnClickListener(this);
		sv_show = (SurfaceView) findViewById(R.id.sv_show);
		vv_show = (VideoView) findViewById(R.id.vv_show);
		File mfile = new File("/mnt/ext_sdcard/testvideo.avi");
		mediaco=new MediaController(this); 
		if(mfile.exists()){  
            //VideoView与MediaController进行关联  
			vv_show.setVideoPath(mfile.getAbsolutePath());  
			vv_show.setMediaController(mediaco);  
            mediaco.setMediaPlayer(vv_show);  
            //让VideiView获取焦点  
            vv_show.requestFocus();  
        }  
		mSurfaceHolder = sv_show.getHolder();
		//动态绘制正弦波的定时器
				mTimer = new Timer();
				mTimerTask = new MyTimerTask();
		initData();
	}
	private void initData() {
		
		// 初始化y轴数据
				centerY = (getWindowManager().getDefaultDisplay().getHeight() - sv_show.getTop()) / 2;
				Y_axis = new int[getWindowManager().getDefaultDisplay().getWidth()];
				for (int i = 1; i < Y_axis.length; i++) {// 计算正弦波
					Y_axis[i - 1] = centerY- (int) (100 * Math.sin(i * 2 * Math.PI / 180));
				}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
	case R.id.button2:
		if(mTimerTask!=null){
			mTimerTask.cancel();
			mTimerTask = null;
		}
	
		ClearDraw();
		currentX = 0;
		oldY = centerY;
		initData();
		SimpleDraw(Y_axis.length-1);//直接绘制正弦波
		break;
	case R.id.button3:
		if(mTimerTask==null){
			mTimerTask = new MyTimerTask();
		}
		ClearDraw();
		initData();
		currentX = 0;
		oldY = centerY;
		mTimer.schedule(mTimerTask, 0, 5);//动态绘制正弦波
		break;
	case R.id.button4:
		vv_show.start();
		break;
	case R.id.button5:
		vv_show.pause();
		break;
	case R.id.button6:
		vv_show.stopPlayback();
		break;
	default:
		break;
	}
	}
	class MyTimerTask extends TimerTask {
		@Override
		public void run() {

			SimpleDraw(currentX);
			currentX++;//往前进
			if (currentX == Y_axis.length - 1) {//如果到了终点，则清屏重来
				ClearDraw();
				currentX = 0;
				oldY = centerY;
			}
		}

	}
	/**
	 * 绘制指定区域
	 */
	void SimpleDraw(int length) {
		if (length == 0)
			oldX = 0;
		Canvas canvas = mSurfaceHolder.lockCanvas(new Rect(oldX, 0, oldX + length,
				getWindowManager().getDefaultDisplay().getHeight()));// 关键:获取画布
		Log.i("Canvas:",
				String.valueOf(oldX) + "," + String.valueOf(oldX + length));

		Paint mPaint = new Paint();
		mPaint.setColor(Color.GREEN);// 画笔为绿色
		mPaint.setStrokeWidth(2);// 设置画笔粗细

		int y;
		for (int i = oldX + 1; i < length; i++) {// 绘画正弦波
			y = Y_axis[i - 1];
			canvas.drawLine(oldX, oldY, i, y, mPaint);
			oldX = i;
			oldY = y;
		}
		mSurfaceHolder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
	}

	void ClearDraw() {
		Canvas canvas = mSurfaceHolder.lockCanvas(null);
		canvas.drawColor(Color.BLACK);// 清除画布
		mSurfaceHolder.unlockCanvasAndPost(canvas);

	}
/*	class GLSurfaceViewRender implements GLSurfaceView.Renderer {  
		
		@Override 
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {  
            Log.i(TAG, "onSurfaceCreated");  
  
            // 设置背景颜色  
            gl.glClearColor(0.0f, 0f, 1f, 0.5f);  
        }  
  
        @Override  
        public void onSurfaceChanged(GL10 gl, int width, int height) {  
            // 设置输出屏幕大小  
            gl.glViewport(0, 0, width, height);  
            Log.i(TAG, "onSurfaceChanged");  
        }  
  
  
        @Override  
        public void onDrawFrame(GL10 gl) {  
            Log.i(TAG, "onDrawFrame");  
            // 清除屏幕和深度缓存(如果不调用该代码, 将不显示glClearColor设置的颜色)  
            // 同样如果将该代码放到 onSurfaceCreated 中屏幕会一直闪动  
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);  
  
        }

    } */ 
}