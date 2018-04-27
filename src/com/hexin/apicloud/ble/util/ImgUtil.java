package com.hexin.apicloud.ble.util;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
/**
 * 网络图片加载工具类
 * @author 军刀
 *
 */
public class ImgUtil {
	
	/** 
	 * 获取网络地址对应的图片 
	 * @param path 
	 * @return bitmap的类型  
	 */  
    public static Bitmap getImage(String path) throws Exception{  
    	 URL url = new URL(path);
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setConnectTimeout(5000);
         conn.setRequestMethod("GET");
         if (conn.getResponseCode() == 200) {
             InputStream inputStream = conn.getInputStream();
             Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
             return bitmap;
         }
         return null;
    }
    public static byte[] getBytes(InputStream is) throws Exception{  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while((len = is.read(buffer))!=-1){  
            bos.write(buffer, 0, len);  
        }  
        is.close();  
        bos.flush();  
        byte[] result = bos.toByteArray();  
        return  result;  
    }
}
