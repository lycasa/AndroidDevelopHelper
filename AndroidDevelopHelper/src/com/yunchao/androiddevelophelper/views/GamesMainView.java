package com.yunchao.androiddevelophelper.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.yunchao.androiddevelophelper.R;
import com.yunchao.androiddevelophelper.adapters.Main_ListViewAdapter;
import com.yunchao.androiddevelophelper.customviews.AbPullListView;
import com.yunchao.androiddevelophelper.global.Conf;
import com.yunchao.androiddevelophelper.listener.AbOnListViewListener;
import com.yunchao.androiddevelophelper.utils.BaseUtils;

public class GamesMainView extends ViewGroup implements OnItemClickListener {
	private Context mcontext;
	private List<String> mdata = new ArrayList<String>();
	private AbPullListView lv_games;
	private Main_ListViewAdapter mListViewAdapter;
	public GamesMainView(Context context) {
		super(context);
		mcontext = context;
		LayoutInflater.from(mcontext).inflate(R.layout.games_main, this, true);
		initdata();
		initview();
	}

	private void initdata() {
		try {
			mdata=BaseUtils.getFileData(Conf.ASSETS_GAMES_PATH, mcontext);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
/*	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		initdata();
		initview();
	}*/
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub

	}
	private void initview() {
	/*	lv_androidutils_text = (AbPullListView) this
				.findViewById(R.id.lv_androidutils_text);*/
		lv_games = (AbPullListView) findViewById(R.id.lv_games);
		mListViewAdapter = new Main_ListViewAdapter(mdata, mcontext);
		lv_games.setAdapter(mListViewAdapter);
		lv_games.setOnItemClickListener(this);
		//打开关闭下拉刷新加载更多功能
		lv_games.setPullRefreshEnable(true); 
		lv_games.setPullLoadEnable(true);
		  //设置进度条的样式
		lv_games.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
		lv_games.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
		lv_games.setAbOnListViewListener(new AbOnListViewListener() {
			
			@Override
			public void onRefresh() {
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Toast.makeText(mContext, "刷新成功！", Toast.LENGTH_SHORT).show();
				lv_games.stopRefresh();
				
			}
			
			@Override
			public void onLoadMore() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Toast.makeText(mContext, "加载更多成功！", Toast.LENGTH_SHORT).show();
				lv_games.stopLoadMore();
			}
		});
		
		
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
}
