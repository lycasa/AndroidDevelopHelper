package com.yunchao.androiddevelophelper.demos.news;

import java.net.MalformedURLException;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.yunchao.androiddevelophelper.R;

public class News_MainActivity extends Activity implements OnItemClickListener {
	public final String RSS_URL = "http://blog.sina.com.cn/rss/1267454277.xml";
	//public final String RSS_URL = "http://news.baidu.com/n?cmd=4&class=internet&pn=1&tn=rss";
	public final String tag = "RSSReader";
	private RSSFeed feed = null;
	private Thread mthread;
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.news_mainactivity);
	/*	try {// 调用getFeed方法,从服务器取得rss提要
			feed = new RssFeed_SAXParser().getFeed(RSS_URL);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}*/

		//showListView(); // 把rss内容绑定到ui界面进行显示
		
		if(mthread==null){
			mthread = new Thread(runnable);
			mthread.start();
		}
		

	}
Runnable runnable = new Runnable() {
	
	@Override
	public void run() {
		try {// 调用getFeed方法,从服务器取得rss提要
			feed = new RssFeed_SAXParser().getFeed(RSS_URL);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
		mHandler.sendEmptyMessage(1);
	}
};
private Handler mHandler = new Handler() {  
    public void handleMessage (Message msg) {//此方法在ui线程运行  
        switch(msg.what) {  
        case 1:  
        	showListView();
            break;  

        }  
    }  
};
	private void showListView() {
		ListView itemlist = (ListView) findViewById(R.id.itemlist);
		if (feed == null) {
			setTitle("访问的RSS无效");
			return;
		}
		SimpleAdapter adapter = new SimpleAdapter(this,
				feed.getAllItemsForListView(),
				android.R.layout.simple_list_item_2, new String[] {
						RSSItem.TITLE, RSSItem.PUBDATE }, new int[] {
						android.R.id.text1, android.R.id.text2 });
		itemlist.setAdapter(adapter); // listview绑定适配器
		itemlist.setOnItemClickListener(this); // 设置itemclick事件代理
		itemlist.setSelection(0);

	}

	public void onItemClick(AdapterView parent, View v, int position, long id) {// itemclick事件代理方法{
		Intent itemintent = new Intent(this, News_ShowDescriptionActivity.class);// 构建一个“意图”，用于指向activity

		Bundle b = new Bundle();// 构建buddle，并将要传递参数都放入buddle
		b.putString("title", feed.getItem(position).getTitle());
		b.putString("description", feed.getItem(position).getDescription());
		b.putString("link", feed.getItem(position).getLink());
		b.putString("pubdate", feed.getItem(position).getPubdate());
		itemintent.putExtra("Android.intent.extra.RSSItem", b); // 用android.intent.extra.INTENT的名字来传递参数
		startActivityForResult(itemintent, 0);
	}

}