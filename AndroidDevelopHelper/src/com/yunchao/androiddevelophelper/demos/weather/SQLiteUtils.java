package com.yunchao.androiddevelophelper.demos.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteUtils {
	/**主属性列_id*/
	static final String ID = "_id";
	/**属性city*/
	static final String CITY = "city";
	/**数据库名称*/
	static final String DB_NAME = "Weather.db";
	/**表名称*/
	static final String DB_TABLE = "City";
	/**数据库版本号*/
	private static final int VERSION = 1;
	private static SQLiteDatabase mySQLite;
	/**创建数据库语句*/
	private static final String DB_CREATE = "CREATE TABLE " + DB_TABLE + " ("+ID+ " INTEGER PRIMARY KEY,"
			+ CITY + " TEXT)";


	/**打开数据库*/
	public static void openDatabase(Context mContext) {
		MyHelper myhelper = new MyHelper(mContext);
		// 这时创建或打开数据库，如果数据库是新创建的则激活SQLiteOpenHelper对象的的onCreate()方法
		mySQLite = myhelper.getWritableDatabase();
	}
	
	/** 插入或更新数据*/
	public static void insert(String city) {
		ContentValues cv = new ContentValues();
		cv.put(ID, 1);
		cv.put(CITY, city);
		/*
		 * 此表中，只有一个数据，并且，只有在第一次为插入数据，其他均要更新数据
		 */
		mySQLite.delete(DB_TABLE, ID+"=?", new String[]{"1"});
		long i = mySQLite.insert(DB_TABLE, "null", cv);
		System.out.println("insert "+i);
	}

	/**  查询数据*/
	public static Cursor fetchData(int id) {
		return mySQLite.query(DB_TABLE, new String[]{CITY}, ID+"="+id, null, null, null, null);
	}
	
	/**关闭数据库*/
	public static void close(){
		if(mySQLite!=null){
			mySQLite.close();
			mySQLite = null;
		}
	}
	
	// 静态类MyHelper继承SQLiteOpenHelper，用于创建数据库。
	private static  class MyHelper extends SQLiteOpenHelper {
		
		public MyHelper(Context context) {
			super(context, DB_NAME, null, VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DB_CREATE);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
		
	}
}
