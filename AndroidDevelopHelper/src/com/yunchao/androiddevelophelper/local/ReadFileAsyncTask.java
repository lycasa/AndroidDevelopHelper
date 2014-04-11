package com.yunchao.androiddevelophelper.local;

import java.io.File;

import com.yunchao.androiddevelophelper.global.Conf;

import android.os.AsyncTask;

public class ReadFileAsyncTask extends AsyncTask<Object, Object, Object> {
@Override
protected void onPreExecute() {
	// TODO Auto-generated method stub
	super.onPreExecute();
}
	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}
@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		File datafile=new File(Conf.DATA_FILE_PATH);
		//new FileRe
	}
}
