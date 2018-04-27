package com.hexin.apicloud.ble.util;
/**
 * 异常工具类
 * @author 军刀
 *
 */
public class ErrorUtil {
	
	/**
	 * 获取异常信息
	 * @param e
	 * @return
	 */
	public static String getStackMsg(Exception e) {  
		  
        StringBuffer sb = new StringBuffer(); 
        sb.append("异常信息:"+e.getMessage()+" 栈信息:");
        StackTraceElement[] stackArray = e.getStackTrace();  
        for (int i = 0; i < stackArray.length; i++) {  
            StackTraceElement element = stackArray[i];  
            sb.append(element.toString() + "\n");  
        }  
        return sb.toString();  
    }  
	
}
