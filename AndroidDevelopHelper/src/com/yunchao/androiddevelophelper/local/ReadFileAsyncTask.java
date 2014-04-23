package com.yunchao.androiddevelophelper.local;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;

import com.yunchao.androiddevelophelper.global.Conf;
import com.yunchao.androiddevelophelper.views.MainActivity;

public class ReadFileAsyncTask extends AsyncTask<Object, Object, Object> {
	ArrayList<String> info = null;
	private Context mcontext;
	
public ReadFileAsyncTask(Context mcontext) {
		this.mcontext = mcontext;
	}
@Override
protected void onPreExecute() {
	// TODO Auto-generated method stub
	super.onPreExecute();
}
	@Override
	protected Object doInBackground(Object... params) {
		
		File file=new File(Conf.DATA_FILE_PATH);
		if (file.exists() && file.isFile()) {
			try {
				String buffer = "";
				info = new ArrayList<String>();
				BufferedReader reader = new BufferedReader(new FileReader(file));
				while ((buffer = reader.readLine()) != null) {
					info.add(buffer);
				}
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return info;
		
	}
@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		((MainActivity)mcontext).sendReadFileEndBroadcast(info);
	}


}
