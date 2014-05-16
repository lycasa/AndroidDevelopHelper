package com.yunchao.androiddevelophelper.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class BaseUtils {

	public static List<String> getFileData(String str,Context context) throws IOException{
		 if(str==null)return null;
		 ArrayList<String> info = null;
		 InputStream assetsfilepath =context.getClass().getResourceAsStream(str);
		 String path = new String(InputStreamToByte(assetsfilepath));
			String paths[] = path.split("\n");
			info = new ArrayList<String>();
			for(int i=0;i<paths.length;i++){
				info.add(paths[i]);
			}
		return info;
	}
    public static byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }
	
	
}
