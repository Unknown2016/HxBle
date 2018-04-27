package com.hexin.apicloud.ble.util;
import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日期工具类
 * @author 军刀
 *
 */
public class DateUtil {
	
    public static String DATE_TO_STRING_NOWYMDT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String DATE_TO_STRING_NOWYMD_PATTERN = "yyyy-MM-dd";
    
    public static String DATE_TO_STRING_NOWY_PATTERN = "yyyy";
    
    public static String DATE_TO_STRING_NOWM_PATTERN = "MM";
    
    public static String DATE_TO_STRING_NOWD_PATTERN = "dd";
    
    public static String DATE_TO_STRING_NOWT_PATTERN = "HH:mm:ss";

    private static SimpleDateFormat simpleDateFormat;
    
    /**
     * 获得当前时间对应的指定格式
     * @param pattern
     * @return
     */
    @SuppressLint("SimpleDateFormat")
	public static String currentFormatDate(String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }
}
