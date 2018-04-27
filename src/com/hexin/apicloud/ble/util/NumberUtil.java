package com.hexin.apicloud.ble.util;
import java.math.BigDecimal;
/**
 * 小数转换整数
 * 解释：毫米转点
 * 工具类
 * @author jundao
 */
public class NumberUtil {
	
	/**
	 * 1mm = 8点 保留一位小数乘以8再转int
	 * @param num
	 * @return
	 */
	public static int  mm2Dot(BigDecimal num){
		return num.setScale(1,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(8)).intValue();
	}
}
