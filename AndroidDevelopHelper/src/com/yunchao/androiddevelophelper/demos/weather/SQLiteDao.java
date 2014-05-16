package com.yunchao.androiddevelophelper.demos.weather;

import android.content.Context;
import android.database.Cursor;

public class SQLiteDao {
	private Context context;
	public SQLiteDao(Context context){
		this.context = context;
	}
	
	public void insert(String city){
		SQLiteUtils.openDatabase(context);
		SQLiteUtils.insert(city);
		SQLiteUtils.close();
	}
	
	public String getCity(){
		String city = null;
		SQLiteUtils.openDatabase(context);
		Cursor cursor = SQLiteUtils.fetchData(1);
		if (cursor.moveToFirst()) {
			city = cursor.getString(0);
		} else {
			city = "上海";
		}
		// 必须关闭
		cursor.close();
		SQLiteUtils.close();
		return city;
	}
}
