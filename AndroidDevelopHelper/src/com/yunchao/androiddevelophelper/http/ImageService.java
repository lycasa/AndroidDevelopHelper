package com.yunchao.androiddevelophelper.http;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageService {
	public static Bitmap getImage(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(6000);
		conn.setRequestMethod("GET");
		
		if(conn.getResponseCode() == 200){
			InputStream instream = conn.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(instream);
			return bitmap;
		}
		
		return null;
		
	}
}
