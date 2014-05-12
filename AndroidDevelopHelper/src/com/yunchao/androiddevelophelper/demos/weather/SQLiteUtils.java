package com.yunchao.androiddevelophelper.demos.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteUtils {
	/**��������_id*/
	static final String ID = "_id";
	/**����city*/
	static final String CITY = "city";
	/**��ݿ����*/
	static final String DB_NAME = "Weather.db";
	/**�����*/
	static final String DB_TABLE = "City";
	/**��ݿ�汾��*/
	private static final int VERSION = 1;
	private static SQLiteDatabase mySQLite;
	/**������ݿ����*/
	private static final String DB_CREATE = "CREATE TABLE " + DB_TABLE + " ("+ID+ " INTEGER PRIMARY KEY,"
			+ CITY + " TEXT)";


	/**����ݿ�*/
	public static void openDatabase(Context mContext) {
		MyHelper myhelper = new MyHelper(mContext);
		// ��ʱ���������ݿ⣬�����ݿ����´������򼤻�SQLiteOpenHelper����ĵ�onCreate()����
		mySQLite = myhelper.getWritableDatabase();
	}
	
	/** �����������*/
	public static void insert(String city) {
		ContentValues cv = new ContentValues();
		cv.put(ID, 1);
		cv.put(CITY, city);
		/*
		 * �˱��У�ֻ��һ����ݣ����ң�ֻ���ڵ�һ��Ϊ������ݣ������Ҫ�������
		 */
		mySQLite.delete(DB_TABLE, ID+"=?", new String[]{"1"});
		long i = mySQLite.insert(DB_TABLE, "null", cv);
		System.out.println("insert "+i);
	}

	/**  ��ѯ���*/
	public static Cursor fetchData(int id) {
		return mySQLite.query(DB_TABLE, new String[]{CITY}, ID+"="+id, null, null, null, null);
	}
	
	/**�ر���ݿ�*/
	public static void close(){
		if(mySQLite!=null){
			mySQLite.close();
			mySQLite = null;
		}
	}
	
	// ��̬��MyHelper�̳�SQLiteOpenHelper�����ڴ�����ݿ⡣
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
