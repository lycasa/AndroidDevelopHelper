package com.yunchao.androiddevelophelper.views;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.FrontiaAccount;
import com.baidu.frontia.FrontiaUser;
import com.baidu.frontia.FrontiaUserQuery;
import com.baidu.frontia.api.FrontiaAuthorization;
import com.baidu.frontia.api.FrontiaAuthorization.MediaType;
import com.baidu.frontia.api.FrontiaAuthorizationListener;
import com.baidu.frontia.api.FrontiaPush;
import com.yunchao.androiddevelophelper.R;
import com.yunchao.androiddevelophelper.global.MyApplication;
import com.yunchao.androiddevelophelper.model.User;

public class QQ_login_mainactivity extends Activity implements OnClickListener{
	private Button btn_qq_login;
	private MyApplication application;
	private Context context;
	//推送服务
	private FrontiaPush mPush = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context=this;
		//application = (MyApplication)context.getApplicationContext();
		setContentView(R.layout.qq_login);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_qq_login = (Button) findViewById(R.id.qq_login);
		btn_qq_login.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.qq_login:
			loginAuthorization(0);
			break;

		default:
			break;
		}
		
	}
	

	   public void loginAuthorization(final int toActivity){
	   	   FrontiaAuthorization auth = Frontia.getAuthorization();
	       auth.authorize(this,MediaType.QZONE.toString(),
	           new FrontiaAuthorizationListener.AuthorizationListener() {
	               @Override
	               public void onSuccess(final FrontiaUser user) {
	                    Frontia.setCurrentAccount(user);
	                    String  mAccessToken = user.getAccessToken();
	                    
	                    /*FrontiaRole mRole = new FrontiaRole("user");
	                    mRole.addMember(user);

	                    mRole.update(new FrontiaRole.CommonOperationListener() {

	                        @Override
	                        public void onSuccess() {
	                     	   Log.d("TAG","role add member update");
	                        }

	                        @Override
	                        public void onFailure(int errCode, String errMsg) {
	                     	   Log.d("TAG","role add member update failure");
	                        }
	                    });*/
	                    
	                    //查询保存用户信息
	                    findQQInfo(toActivity,mAccessToken);
	               }

	               @Override
	               public void onFailure(final int errCode, final String errMsg) {
	            	    //showToast("授权错误: " + errMsg);
	               }

	               @Override
	               public void onCancel() {
	            		//showToast("授权取消");  
	               }
	           });
	   }
	   /**
	    * 描述：QQ登录
	    * @throws 
	    */
	   public void findQQInfo(final int toActivity,final String accessToken){
		   //showProgressDialog("查询QQ用户信息...");
		   FrontiaAccount mFrontiaAccount =  Frontia.getCurrentAccount();
	       FrontiaUserQuery query = new FrontiaUserQuery();
	       query = query.nameEqual(mFrontiaAccount.getName());
	       FrontiaUser.findUsers(query, new FrontiaUser.FetchUserListener() {
	           @Override
	           public void onSuccess(List<FrontiaUser.FrontiaUserDetail> userList) {
	        	      // removeProgressDialog();
	        	       User loginUser = null;
	        	       FrontiaUser.FrontiaUserDetail user = null;
	                   if(userList!=null && userList.size()>0){
	                	   user = userList.get(0);
	                   }
	                   if(user!=null){
	                       Log.d("TAG", "头像:"+user.getHeadUrl());
	                       //登录成功保存
	                       loginUser = new User();
	                       loginUser.setuId(user.getId());
	                       loginUser.setName(user.getName());
	                       loginUser.setPhotoUrl(user.getHeadUrl());
	                       loginUser.setSex(user.getSex().name());
	                       loginUser.setAccessToken(accessToken);
	                       loginUser.setLoginUser(true);
	                      // login(loginUser);
	                	  // saveUserData(loginUser);
	                	   if(toActivity==0){
	                       	   //Intent intent = new Intent(MainActivity.this,LoginActivity.class);
	       					   //startActivityForResult(intent,1);
	                       }else if(toActivity==1){
	                       	  /* Intent intent = new Intent(MainActivity.this,FriendActivity.class);
	    					   startActivity(intent);*/
	                       }
	                   }else{
	                	   //showToast("未找到登录用户");
	                   }
	                   
	                   
	           }

	           @Override
	           public void onFailure(int errCode, String errMsg) {
	        	   //removeProgressDialog();
	        	  // showToast("errCode: " + errCode+ ", errMsg: " + errMsg);
	           }
	       });
	   }
	   /**
	    * 
	    * 描述：登录并启动推送服务
	    * @param u
	    * @throws  
	    */   
	   public void login(User u){
		    application.mUser = u;
		    //侧边栏刷新
			//mMainMenuFragment.initMenu();
			Log.d("TAG", "----启动推送服务----");
			mPush = Frontia.getPush();
			//启动推送服务
		    //PushUtil.startPushService(mPush,QQ_login_mainactivity.this);
		   
			   if(application.mUser == null || application.mUser.getAccessToken() == null){
				   return;
			   }
		       if(!mPush.isPushWorking()){
		    	   mPush.start(application.mUser.getAccessToken());
		       }
	   }
}
