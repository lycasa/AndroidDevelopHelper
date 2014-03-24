package com.yunchao.androiddevelophelper.views;

import java.util.ArrayList;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.FrontiaUser;
import com.baidu.frontia.api.FrontiaAuthorization;
import com.baidu.frontia.api.FrontiaAuthorization.MediaType;
import com.baidu.frontia.api.FrontiaAuthorizationListener.AuthorizationListener;
import com.baidu.frontia.api.FrontiaAuthorizationListener.UserInfoListener;
import com.yunchao.androiddevelophelper.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Login_mainActivity extends Activity implements OnClickListener{
	private Button baidu_login,baidu_cancel,baidu_status,baidu_userinfo;
	private TextView mResultTextView;
    private final static String Scope_Basic = "basic";
	private final static String Scope_Netdisk = "netdisk";
	private FrontiaAuthorization mAuthorization;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main);
		mAuthorization = Frontia.getAuthorization();
		initview();
	}

	private void initview() {
		// TODO Auto-generated method stub
		baidu_login = (Button) findViewById(R.id.baidu_login);
		baidu_login.setOnClickListener(this);
		baidu_cancel = (Button) findViewById(R.id.baidu_cancel);
		baidu_cancel.setOnClickListener(this);
		baidu_status = (Button) findViewById(R.id.baidu_status);
		baidu_status.setOnClickListener(this);
		baidu_userinfo = (Button) findViewById(R.id.baidu_userinfo);
		baidu_userinfo.setOnClickListener(this);
		mResultTextView = (TextView) findViewById(R.id.tv_show);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.baidu_login:
			startBaidu();
			break;
		case R.id.baidu_cancel:
			startBaiduLogout();
			break;
		case R.id.baidu_status:
			startBaiduStatus();
			break;
		case R.id.baidu_userinfo:
			startBaiduUserInfo();
			break;

		default:
			break;
		}
	}
	

	protected void startBaidu() {
		ArrayList<String> scope = new ArrayList<String>();
    	scope.add(Scope_Basic);
    	scope.add(Scope_Netdisk);
		mAuthorization.authorize(this,FrontiaAuthorization.MediaType.BAIDU.toString(), scope, new AuthorizationListener() {

					@Override
					public void onSuccess(FrontiaUser result) {
					    Frontia.setCurrentAccount(result);
						if (null != mResultTextView) {
                            mResultTextView.setText(
                                    "social id: " + result.getId() + "\n"
                                            + "token: " + result.getAccessToken() + "\n"
                                            + "expired: " + result.getExpiresIn());
						}
					}

					@Override
					public void onFailure(int errCode, String errMsg) {
						if (null != mResultTextView) {
							mResultTextView.setText("errCode:" + errCode
									+ ", errMsg:" + errMsg);
						}
					}

					@Override
					public void onCancel() {
						if (null != mResultTextView) {
							mResultTextView.setText("cancel");
						}
					}

				});
	}
	protected void startBaiduLogout() {
		boolean result = mAuthorization.clearAuthorizationInfo(
				FrontiaAuthorization.MediaType.BAIDU.toString());
		if (result) {
		    Frontia.setCurrentAccount(null);
			mResultTextView.setText("百度退出成功");
		} else {
			mResultTextView.setText("百度退出失败");
		}
	}
	protected void startBaiduStatus() {
		boolean result = mAuthorization.isAuthorizationReady(FrontiaAuthorization.MediaType.BAIDU.toString());
		if (result) {
			mResultTextView.setText("已经登录百度帐号");
		} else {
			mResultTextView.setText("未登录百度帐号");
		}
	}
	protected void startBaiduUserInfo() {
		userinfo(MediaType.BAIDU.toString());
		
	}
	private void userinfo(String accessToken) {
		mAuthorization.getUserInfo(accessToken, new UserInfoListener() {

			@Override
			public void onSuccess(FrontiaUser.FrontiaUserDetail result) {
				if (null != mResultTextView) {
					String resultStr = "username:" + result.getName() + "\n"
							+ "birthday:" + result.getBirthday() + "\n"
							+ "city:" + result.getCity() + "\n"
							+ "province:" + result.getProvince() + "\n"
							+ "sex:" + result.getSex() + "\n"
							+ "pic url:" + result.getHeadUrl() + "\n";
					mResultTextView.setText(resultStr);
				}
			}

			@Override
			public void onFailure(int errCode, String errMsg) {
				if (null != mResultTextView) {
					mResultTextView.setText("errCode:" + errCode
							+ ", errMsg:" + errMsg);
				}
			}
			
		});
	}
}
