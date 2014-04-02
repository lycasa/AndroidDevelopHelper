package com.yunchao.androiddevelophelper.views;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


import com.baidu.frontia.Frontia;
import com.baidu.frontia.api.FrontiaSocialShare;
import com.baidu.frontia.api.FrontiaSocialShareContent;
import com.baidu.frontia.api.FrontiaSocialShareListener;
import com.baidu.frontia.api.FrontiaAuthorization.MediaType;
import com.baidu.frontia.api.FrontiaSocialShare.FrontiaTheme;
import com.yunchao.androiddevelophelper.R;
import com.yunchao.androiddevelophelper.global.Conf;

public class ListView_Show_Activity extends Activity implements OnClickListener {
	private Button btn_back;
	private TextView tv_listview_item_show_title,tv_listview_item_show_content,tv_listview_item_show_share;
	private FrontiaSocialShare mSocialShare;
	private FrontiaSocialShareContent mImageContent = new FrontiaSocialShareContent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_item_show);
		initview();
		initdata();
	}

	private void initdata() {
		Intent mintent =getIntent();
		String str=mintent.getStringExtra("str");
		tv_listview_item_show_title.setText(str);
		
		 
        mSocialShare = Frontia.getSocialShare();
		mSocialShare.setContext(this);
		mSocialShare.setClientId(MediaType.WEIXIN.toString(), Conf.APIKEY);
		mImageContent.setTitle(this.getResources().getString(R.string.app_name));
		mImageContent.setContent("dkgjasdljgkdjgkldjgkldjgkdjgkdjgkdjgkgjdkgjdk");
		/*mImageContent.setLinkUrl("http://www.418log.org/");
		mImageContent.setImageUri(Uri.parse("http://apps.bdimg.com/developer/static/04171450/developer/images/icon/terminal_adapter.png"));*/
	}

	private void initview() {
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		tv_listview_item_show_title = (TextView) findViewById(R.id.tv_listview_item_show_title);
		tv_listview_item_show_content = (TextView) findViewById(R.id.tv_listview_item_show_content);
		tv_listview_item_show_share = (TextView) findViewById(R.id.tv_listview_item_show_share);
		tv_listview_item_show_share.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.tv_listview_item_show_share:
			mSocialShare.show(this.getWindow().getDecorView(), mImageContent, FrontiaTheme.LIGHT,  new ShareListener());
			break;

		default:
			break;
		}
		
	}
	private class ShareListener implements FrontiaSocialShareListener {

		@Override
		public void onSuccess() {
			Log.d("TAG","share success");
		}

		@Override
		public void onFailure(int errCode, String errMsg) {
			Log.d("TAG","share errCode "+errCode);
		}

		@Override
		public void onCancel() {
			Log.d("TAG","cancel ");
		}
		
	}
}
